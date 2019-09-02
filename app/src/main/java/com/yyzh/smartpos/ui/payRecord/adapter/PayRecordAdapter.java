package com.yyzh.smartpos.ui.payRecord.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yyzh.smartpos.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by WJ on 2019/8/26.
 */

public class PayRecordAdapter extends Adapter {
    private Context mContext;
    private final LayoutInflater inflater;
    private List<Object> mList = new ArrayList<>();

    public PayRecordAdapter(Context mContext) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pay_record_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public void setData(List<Object> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.tv_state)
        TextView tvState;
        @BindView(R.id.tv_address)
        TextView tvAddress;
        @BindView(R.id.tv_zhangqi)
        TextView tvZhangqi;
        @BindView(R.id.tv_money)
        TextView tvMoney;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
