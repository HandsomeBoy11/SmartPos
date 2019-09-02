package com.yyzh.smartpos.ui.payRecord.view;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyzh.commonlibrary.base.BaseFragment;
import com.yyzh.commonlibrary.base.NoViewModel;
import com.yyzh.commonlibrary.utils.DisplayUtil;
import com.yyzh.smartpos.R;
import com.yyzh.smartpos.ui.payRecord.adapter.PayRecordAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WJ on 2019/8/26.
 */

public class PayRecoredFragment extends BaseFragment<NoViewModel> {
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_more_filter)
    TextView tvMoreFilter;
    @BindView(R.id.rv_record_list)
    RecyclerView rvRecordList;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    private PayRecordAdapter payRecordAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_pay_recored;
    }

    @Override
    protected void initView() {
        rvRecordList.setLayoutManager(new LinearLayoutManager(mActivity));
        payRecordAdapter = new PayRecordAdapter(mContext);
        rvRecordList.setAdapter(payRecordAdapter);
        rvRecordList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                if(position==0){
                    outRect.top= DisplayUtil.dip2px(7);
                }
                outRect.bottom= DisplayUtil.dip2px(7);
            }
        });

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_all, R.id.tv_time, R.id.tv_price, R.id.tv_more_filter, R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_all://全部
                break;
            case R.id.tv_time://时间
                break;
            case R.id.tv_price://缴费金额
                break;
            case R.id.tv_more_filter://更多条件
                break;
            case R.id.iv_more://更多数据显示
                startActivity(new Intent(getActivity(),DataTotalActivity.class));
                break;
        }
    }
}
