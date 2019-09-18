package com.yyzh.smartpos.ui.main.view.activity;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.yyzh.commonlibrary.utils.LogUtil;
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
    @BindView(R.id.tv_jiaofei)
    TextView tvJiaofei;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    private long clickTime;

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

    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        initFragment(savedInstanceState);
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
            fragments.add(this.propertyPayFragment);
            fragments.add(payRecoredFragment);
            transaction.add(R.id.fl_container, this.propertyPayFragment, "PropertyPayFragment");
            transaction.add(R.id.fl_container, payRecoredFragment, "PayRecoredFragment");

        }
        transaction.commit();
        changeFragment(currentTabPosition);
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

    @OnClick({ R.id.tv_jiaofei, R.id.tv_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
}
