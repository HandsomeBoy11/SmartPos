package com.yyzh.commonlibrary.base;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yyzh.commonlibrary.utils.ClassUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<T extends ViewModel> extends Fragment {
    protected T viewModel;
    protected View rootView;
    private Unbinder unbinder;
    protected Context mContext;
    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
        if(context instanceof Activity){
            mActivity= ((Activity) context);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        initViewModel();
        initData();
        return rootView;
    }

    protected abstract int getLayoutResource();

    protected abstract void initView();

    private void initViewModel() {
        Class<T> viewModelClass = new ClassUtil<T>().getViewModel(this);
        if (viewModelClass != null)
            viewModel = ViewModelProviders.of(this).get(viewModelClass);
    }

    protected abstract void initData();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
