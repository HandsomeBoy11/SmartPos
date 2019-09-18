package com.yyzh.smartpos.ui.main.viewModel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.yyzh.commonlibrary.base.BaseViewModel;
import com.yyzh.commonlibrary.base.RxHelper;
import com.yyzh.smartpos.net.Api;
import com.yyzh.smartpos.net.HostType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainViewModel extends BaseViewModel {

    public MutableLiveData<String> getString(String str) {
        MutableLiveData<String> liveData = new MutableLiveData<>();
        Disposable disposable = Api.getDefault(HostType.TYPE_COUNT).getData(str)
                .compose(RxHelper.rxSchedulerHelper())
                .subscribe(str1 -> {
                    liveData.postValue(str1);
                }, throwable -> {
                    Log.e("", throwable.toString());
                });
        addDisposable(disposable);
        return liveData;
    }

    public MutableLiveData<List<String>> getProjectList() {
        MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        List<String> list = new ArrayList<>();
        list.add("远洋国际中心三期");
        list.add("北京远洋山水家园");
        list.add("北京远洋万和城");
        list.add("北京远洋沁山水");
        list.add("北京远洋山水家园（北区）");
        liveData.postValue(list);
        return liveData;
    }

    public MutableLiveData<List<String>> getTower() {
        MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        List<String> list = new ArrayList<>();
        list.add("1号楼");
        list.add("2号楼");
        list.add("3号楼");
        list.add("4号楼");
        list.add("5号楼");
        list.add("6号楼");
        list.add("7号楼");
        list.add("8号楼");
        liveData.postValue(list);
        return liveData;
    }

    public MutableLiveData<List<String>> getTowerCell(String cell) {
        MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        String s = cell.substring(0, 1);
        int num = Integer.parseInt(s);
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            list.add(i + "单元");
        }
        liveData.postValue(list);
        return liveData;
    }

    public LiveData<List<String>> getFloor() {
        MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        List<String> list = new ArrayList<>();
        list.add("1楼层");
        list.add("2楼层");
        list.add("3楼层");
        list.add("4楼层");
        list.add("5楼层");
        list.add("6楼层");
        list.add("7楼层");
        list.add("8楼层");
        list.add("9楼层");
        liveData.postValue(list);
        return liveData;
    }

    public LiveData<List<String>> getHouseNum(String floor) {
        MutableLiveData<List<String>> liveData = new MutableLiveData<>();
        String s = floor.substring(0, 1);
        int num = Integer.parseInt(s);
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            list.add(i + "号门");
        }
        liveData.postValue(list);
        return liveData;
    }
}
