package com.yyzh.smartpos.ui.propertyPayCast.view.fragment;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yyzh.commonlibrary.base.BaseFragment;
import com.yyzh.commonlibrary.utils.SPUtils;
import com.yyzh.smartpos.R;
import com.yyzh.smartpos.ui.main.callBack.FragmentCallBack;
import com.yyzh.smartpos.ui.main.view.activity.LoginActivity;
import com.yyzh.smartpos.ui.main.viewModel.MainViewModel;
import com.yyzh.smartpos.utils.SpConst;
import com.yyzh.smartpos.weight.selectCell.CellDialog;
import com.yyzh.smartpos.weight.selectProject.ProjectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WJ on 2019/8/26.
 */

public class PropertyPayFragment extends BaseFragment<MainViewModel> {
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

    private ProjectDialog projectDialog;
    private List<String> projectList = new ArrayList<>();
    private CellDialog cellDialog;
    private CellDialog floorDialog;

    private FragmentCallBack callBack;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentCallBack) {
            callBack = ((FragmentCallBack) context);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_property_pay;
    }

    @Override
    protected void initView() {
        if (callBack != null) {
            Bundle bundle = new Bundle();
            bundle.putString("tvName", "测试专用");
            callBack.setFragmentBundle(bundle);
        }
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
        this.viewModel.getHouseNum(floor).observe(this, strings -> {
            if (floorDialog != null)
                floorDialog.setData(strings);
        });
    }

    @Override
    protected void initData() {
        getProjectList();
    }

    private void getProjectList() {
        this.viewModel.getProjectList().observe(this, strings -> {
            projectList.clear();
            projectList.addAll(strings);
        });
    }

    @OnClick({R.id.ll_project, R.id.ll_house_num, R.id.ll_cell, R.id.tv_search, R.id.tv_loginout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_project://选择项目
                if (projectDialog != null)
                    projectDialog.show(mActivity.getFragmentManager(), "progect", projectList);
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
                SPUtils.setSharedBooleanData(mActivity, SpConst.ISLOGIN, false);
                Intent intent = new Intent(mActivity, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                mActivity.finish();
                break;
        }
    }

    private void getTower() {
        this.viewModel.getTower().observe(this, strings -> {
            if (cellDialog != null)
                cellDialog.show(mActivity.getFragmentManager(), "progect", strings);
        });
    }

    private void getFloor() {
        this.viewModel.getFloor().observe(this, strings -> {
            if (floorDialog != null)
                floorDialog.show(mActivity.getFragmentManager(), "progect", strings);
        });
    }
}
