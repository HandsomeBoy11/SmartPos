package com.yyzh.smartpos.weight.selectProject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.yyzh.commonlibrary.base.BaseDialog;
import com.yyzh.smartpos.R;
import com.yyzh.smartpos.weight.MaxHeightLinearView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by WJ on 2019/8/23.
 */

public class ProjectDialog extends BaseDialog {
    private static final long TIME = 300;       // 动画的时间

    private int mHeightPixels;
    private MaxHeightLinearView llBg;
    private RecyclerView rvList;
    private ProjectAdapter projectAdapter;
    private List<String> mData;
    private ReturnProject returnProject;

    @Override
    public int setGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    public float setAlpha() {
        return 0.3f;
    }

    @Override
    protected void init() {
        findView();
        initData();
        initScreen();
        // 打开的动画
        openAnim();
    }

    private void findView() {
        llBg = (MaxHeightLinearView) mView.findViewById(R.id.ll_bg);
        rvList = (RecyclerView) mView.findViewById(R.id.rv_list);
        rvList.setLayoutManager(new LinearLayoutManager(mActivity));
        rvList.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        projectAdapter = new ProjectAdapter(mActivity);
        rvList.setAdapter(projectAdapter);
        projectAdapter.setItemClickCallBack(new ProjectAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int index, String obj) {
                if (returnProject != null) {
                    returnProject.retrunProjectInfo(index, obj);
                }
                closeAnim();
            }
        });

        if (getDialog() != null) {
            getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface anInterface, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                        closeAnim();
                        return true;
                    }
                    return false;
                }
            });
        }

    }

    private void initData() {
        if (mData != null) {
            projectAdapter.setData(mData);
        }
    }

    private void initScreen() {
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 获取屏幕的高度
        mHeightPixels = dm.heightPixels;
//        getDialog().setCancelable(false);
//        getDialog().setCanceledOnTouchOutside(false);
    }

    public void openAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llBg, "translationY", mHeightPixels, 0);
        objectAnimator.setDuration(TIME);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }

    public void closeAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llBg, "translationY", 0, mHeightPixels);
        objectAnimator.setDuration(TIME);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                setShow(false);
                dismiss();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_project;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void show(FragmentManager manager, String tag, List<String> mData) {
        this.mData = mData;
        show(manager, tag, false);
    }

    public void setReturnProject(ReturnProject returnProject) {
        this.returnProject = returnProject;
    }

    public interface ReturnProject {
        void retrunProjectInfo(int position, String projectName);
    }
}
