package com.yyzh.smartpos.ui.main.view.activity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yyzh.commonlibrary.base.BaseActivity;
import com.yyzh.commonlibrary.base.BaseFragment;
import com.yyzh.commonlibrary.utils.SPUtils;
import com.yyzh.smartpos.R;
import com.yyzh.smartpos.ui.payRecord.view.PayRecoredFragment;
import com.yyzh.smartpos.ui.propertyPayCast.view.fragment.PropertyPayFragment;
import com.yyzh.smartpos.ui.main.viewModel.MainViewModel;
import com.yyzh.smartpos.utils.SpConst;
import com.yyzh.smartpos.weight.selectCell.CellDialog;
import com.yyzh.smartpos.weight.selectProject.ProjectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MainActivity extends BaseActivity<MainViewModel> {


    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.ll_project)
    LinearLayout llProject;
    @BindView(R.id.tv_cell)
    TextView tvCell;
    @BindView(R.id.ll_cell)
    LinearLayout llCell;
    @BindView(R.id.tv_house_num)
    TextView tvHouseNum;
    @BindView(R.id.ll_house_num)
    LinearLayout llHouseNum;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_loginout)
    TextView tvLoginout;
    @BindView(R.id.tv_jiaofei)
    TextView tvJiaofei;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    private long clickTime;
    private ProjectDialog projectDialog;
    private List<String> projectList = new ArrayList<>();
    private CellDialog cellDialog;
    private CellDialog floorDialog;

    private BaseFragment currentFragment;
    private List<BaseFragment> fragments;
    private int currentTabPosition;
    private PropertyPayFragment propertyPayFragment;
    private PayRecoredFragment payRecoredFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initDialog();
    }

    private void initDialog() {
        if (projectDialog == null) {
            projectDialog = new ProjectDialog();
        }
        projectDialog.setReturnProject(new ProjectDialog.ReturnProject() {
            @Override
            public void retrunProjectInfo(int position, String projectName) {
                tvProject.setText(projectName);
            }
        });

        if (cellDialog == null) {
            cellDialog = new CellDialog();
        }
        cellDialog.setReturnCell(new CellDialog.ReturnCell() {
            @Override
            public void getCell(int position, String cell) {
                getTowerCell(cell);
            }

            @Override
            public void returnCell(int position, String cell) {
                tvCell.setText(cell);
            }
        });
        if (floorDialog == null) {
            floorDialog = new CellDialog();
        }
        floorDialog.setReturnCell(new CellDialog.ReturnCell() {
            @Override
            public void getCell(int position, String floor) {
                getHouseNum(floor);
            }

            @Override
            public void returnCell(int position, String cell) {
                tvHouseNum.setText(cell);
            }
        });
    }

    private void getTowerCell(String cell) {
        this.viewModel.getTowerCell(cell).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (cellDialog != null) {
                    cellDialog.setData(strings);
                }
            }
        });
    }

    private void getHouseNum(String floor) {
        this.viewModel.getHouseNum(floor).observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (floorDialog != null)
                    floorDialog.setData(strings);
            }
        });
    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        initFragment(savedInstanceState);
        getProjectList();
    }

    private void initFragment(Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        currentTabPosition = 0;
        if (savedInstanceState != null) {
            propertyPayFragment = ((PropertyPayFragment) manager.findFragmentByTag("PropertyPayFragment"));
            payRecoredFragment = ((PayRecoredFragment) manager.findFragmentByTag("PayRecoredFragment"));
            currentTabPosition = (int) savedInstanceState.get("tabIndex");
        } else {
            propertyPayFragment = new PropertyPayFragment();
            payRecoredFragment = new PayRecoredFragment();
            fragments.add(propertyPayFragment);
            fragments.add(payRecoredFragment);
            transaction.add(R.id.fl_container, propertyPayFragment, "PropertyPayFragment");
            transaction.add(R.id.fl_container, payRecoredFragment, "PayRecoredFragment");

        }
        transaction.commit();
        changeFragment(currentTabPosition);
    }

    private void getProjectList() {
        this.viewModel.getProjectList().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                projectList.clear();
                projectList.addAll(strings);
            }
        });
    }

    private void changeFragment(int position) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        BaseFragment fragment;
        if (position == 1) {
            PropertyPayFragment fragmentOne = (PropertyPayFragment) manager.findFragmentByTag("PropertyPayFragment");
            Bundle arguments = fragmentOne.getArguments();
            fragment = (BaseFragment) manager.findFragmentByTag("PayRecoredFragment");
            fragment.setArguments(arguments);
        } else {
            fragment = fragments.get(position);
        }

        for (int i = 0; i < fragments.size(); i++) {
            if (position == i) {
                transaction.show(fragment);
            } else {
                transaction.hide(fragments.get(i));
            }
        }
        transaction.commitAllowingStateLoss();
        currentTabPosition = position;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tabIndex", currentTabPosition);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - clickTime) > 2000) {
                Toast.makeText(this, "再按一次后退键退出程序", Toast.LENGTH_SHORT).show();
                clickTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.ll_project, R.id.ll_house_num, R.id.ll_cell, R.id.tv_search, R.id.tv_loginout, R.id.tv_jiaofei, R.id.tv_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_project://选择项目
                if (projectDialog != null)
                    projectDialog.show(getFragmentManager(), "progect", projectList);
                break;
            case R.id.ll_cell://楼栋单元
                getTower();
                break;
            case R.id.ll_house_num:// 楼层房间
                getFloor();
                break;
            case R.id.tv_search://查找账单
                break;
            case R.id.tv_loginout://退出登录
                SPUtils.setSharedBooleanData(this, SpConst.ISLOGIN, false);
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_jiaofei://物业缴费
                if (currentTabPosition != 0) {
                    tvDes.setText("物业费账单查找");
                    tvJiaofei.setBackgroundColor(getResources().getColor(R.color.orange));
                    tvJiaofei.setTextColor(getResources().getColor(R.color.white));
                    tvRecord.setBackgroundColor(getResources().getColor(R.color.white));
                    tvRecord.setTextColor(getResources().getColor(R.color.text_color));
                    changeFragment(0);
                }
                break;
            case R.id.tv_record://缴费记录
                if (currentTabPosition != 1) {
                    tvDes.setText("缴费记录");
                    tvRecord.setBackgroundColor(getResources().getColor(R.color.orange));
                    tvRecord.setTextColor(getResources().getColor(R.color.white));
                    tvJiaofei.setBackgroundColor(getResources().getColor(R.color.white));
                    tvJiaofei.setTextColor(getResources().getColor(R.color.text_color));
                    changeFragment(1);
                }
                break;
        }
    }

    private void getTower() {
        this.viewModel.getTower().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (cellDialog != null)
                    cellDialog.show(getFragmentManager(), "progect", strings);
            }
        });
    }

    private void getFloor() {
        this.viewModel.getFloor().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (floorDialog != null)
                    floorDialog.show(getFragmentManager(), "progect", strings);
            }
        });
    }
}
