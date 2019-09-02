package com.yyzh.smartpos.ui.payRecord.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.openxu.cview.chart.barchart.BarHorizontalChart;
import com.openxu.cview.chart.barchart.BarVerticalChart;
import com.openxu.cview.chart.bean.BarBean;
import com.yyzh.commonlibrary.base.BaseActivity;
import com.yyzh.commonlibrary.base.NoViewModel;
import com.yyzh.commonlibrary.utils.DisplayUtil;
import com.yyzh.smartpos.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by WJ on 2019/8/26.
 */

public class DataTotalActivity extends BaseActivity<NoViewModel> {
    @BindView(R.id.chart1)
    BarHorizontalChart chart1;
    @BindView(R.id.chart2)
    BarVerticalChart chart2;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_total_order)
    TextView tvTotalOrder;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_total_refund)
    TextView tvTotalRefund;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_total;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData(Bundle savedInstanceState) {
        chart1.setBarSpace(DisplayUtil.dip2px(1));  //双柱间距
        chart1.setBarItemSpace(DisplayUtil.dip2px(20));  //柱间距
        chart1.setDebug(false);
        chart1.setBarNum(2);
        chart1.setBarColor(new int[]{Color.parseColor("#5F93E7"), Color.parseColor("#F28D02"), Color.parseColor("#FF0000"),});
        //X轴
        List<String> strXList = new ArrayList<>();
        Random random = new Random();
        //柱状图数据
        List<List<BarBean>> dataList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            //此集合为柱状图上一条数据，集合中包含几个实体就是几个柱子
            List<BarBean> list = new ArrayList<>();
            list.add(new BarBean(random.nextInt(30), "lable1"));
            list.add(new BarBean(random.nextInt(20), "lable2"));
            dataList.add(list);
            strXList.add((i + 1) + "月");
        }
        chart1.setLoading(false);
        chart1.setData(dataList, strXList);


        chart2.setBarSpace(DisplayUtil.dip2px(1));  //双柱间距
        chart2.setBarItemSpace(DisplayUtil.dip2px(10));  //柱间距
        chart2.setDebug(false);
        chart2.setBarNum(3);
        chart2.setBarColor(new int[]{Color.parseColor("#5F93E7"), Color.parseColor("#F28D02"), Color.parseColor("#FF0000"),});
//        //X轴
//        List<String> strXList = new ArrayList<>();
//        Random random = new Random();
//        //柱状图数据
//        List<List<BarBean>> dataList = new ArrayList<>();
        strXList.clear();
        dataList.clear();
        for (int i = 0; i < 12; i++) {
            //此集合为柱状图上一条数据，集合中包含几个实体就是几个柱子
            List<BarBean> list = new ArrayList<>();
            list.add(new BarBean(random.nextInt(100), "已收款\n100元"));
            list.add(new BarBean(random.nextInt(20), "已退款\n100元"));
            dataList.add(list);
            strXList.add("19-" + (i + 1) + "月");
        }
        chart2.setLoading(false);
        chart2.setData(dataList, strXList);
    }


    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
