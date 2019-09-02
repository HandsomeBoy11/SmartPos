package com.yyzh.smartpos.ui.main.viewModel;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.yyzh.commonlibrary.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends BaseViewModel {

    public MutableLiveData<String> getString(String str){
         MutableLiveData<String> liveData=new MutableLiveData<>();
         liveData.postValue(str);
         return liveData;
    }

    public MutableLiveData<List<String>> getProjectList(){
        MutableLiveData<List<String>> liveData=new MutableLiveData<>();
        List<String> list=new ArrayList<>();
        list.add("远洋国际中心三期");
        list.add("北京远洋山水家园");
        list.add("北京远洋万和城");
        list.add("北京远洋沁山水");
        list.add("北京远洋山水家园（北区）");
        liveData.postValue(list);
        return liveData;
    }

    public MutableLiveData<List<String>> getTower() {
        MutableLiveData<List<String>> liveData=new MutableLiveData<>();
        List<String> list=new ArrayList<>();
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
        MutableLiveData<List<String>> liveData=new MutableLiveData<>();
        String s = cell.substring(0, 1);
        int num=Integer.parseInt(s);
        List<String> list=new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            list.add(i+"单元");
        }
        liveData.postValue(list);
        return liveData;
    }

    public LiveData<List<String>> getFloor() {
        MutableLiveData<List<String>> liveData=new MutableLiveData<>();
        List<String> list=new ArrayList<>();
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
        MutableLiveData<List<String>> liveData=new MutableLiveData<>();
        String s = floor.substring(0, 1);
        int num=Integer.parseInt(s);
        List<String> list=new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            list.add(i+"号门");
        }
        liveData.postValue(list);
        return liveData;
    }
}
