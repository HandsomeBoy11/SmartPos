package com.yyzh.smartpos.weight.selectCell;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.yyzh.commonlibrary.base.BaseDialog;
import com.yyzh.smartpos.R;
import com.yyzh.smartpos.weight.MaxHeightLinearView;
import com.yyzh.smartpos.weight.selectProject.ProjectAdapter;

import java.util.List;

/**
 * Created by WJ on 2019/8/23.
 */

public class CellDialog extends BaseDialog {
    private static final long TIME = 300;       // 动画的时间

    private int mHeightPixels;
    private MaxHeightLinearView llBg;
    private RecyclerView rvList1;
    private RecyclerView rvList2;
    private List<String> mData;
    private CellAdapter oneAdapter;
    private CellAdapter twoAdapter;
    private ReturnCell returnCell;
    private String cell;

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
        rvList1 = (RecyclerView) mView.findViewById(R.id.rv_list1);
        rvList2 = (RecyclerView) mView.findViewById(R.id.rv_list2);
        rvList1.setLayoutManager(new LinearLayoutManager(mActivity));
        rvList2.setLayoutManager(new LinearLayoutManager(mActivity));
        rvList1.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        rvList2.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        oneAdapter = new CellAdapter(mActivity);
        twoAdapter = new CellAdapter(mActivity);
        rvList1.setAdapter(oneAdapter);
        rvList2.setAdapter(twoAdapter);
        oneAdapter.setItemClickCallBack(new CellAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int index, String obj) {
                cell = "";
                cell = obj;
                if (returnCell != null) {
                    returnCell.getCell(index, obj);
                }
            }
        });
        twoAdapter.setItemClickCallBack(new CellAdapter.ItemClickCallBack() {
            @Override
            public void onItemClick(int index, String obj) {
                if (returnCell != null) {
                    cell += obj;
                    returnCell.returnCell(index, cell);
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
            oneAdapter.setData(mData);
        }
    }

    private void initScreen() {
        WindowManager wm = (WindowManager) mActivity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 获取屏幕的高度
        mHeightPixels = dm.heightPixels;
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
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
        return R.layout.dialog_cell;
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

    public void setData(List<String> mData) {
        twoAdapter.setData(mData);
    }

    public void setReturnCell(ReturnCell returnCell) {
        this.returnCell = returnCell;
    }

    public interface ReturnCell {
        void getCell(int position, String cell);

        void returnCell(int position, String cell);
    }
}
