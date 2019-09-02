package com.yyzh.commonlibrary.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.yyzh.commonlibrary.utils.ClassUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity<T extends ViewModel> extends AppCompatActivity {
    protected T viewModel;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        AppManager.getAppManager().addActivity(this);
        initView();
        initViewModel();
        loadData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    private void initViewModel() {

        Class<T> viewModelClass = new ClassUtil<T>().getViewModel(this);
        if (viewModelClass != null)
        viewModel = ViewModelProviders.of(this).get(viewModelClass);
    }

    protected abstract void loadData(Bundle savedInstanceState);

    protected void startAct(Class c){
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }
    protected void startAct(Class c,boolean finish){
        Intent intent = new Intent(this, c);
        startActivity(intent);
        if(finish)
            finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().finishActivity(this);
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
