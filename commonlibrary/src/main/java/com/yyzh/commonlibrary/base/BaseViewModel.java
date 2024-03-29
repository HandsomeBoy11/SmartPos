package com.yyzh.commonlibrary.base;


import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;

    protected void addDisposable(Disposable disposable){
        if(compositeDisposable==null){
            compositeDisposable=new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(compositeDisposable!=null&&compositeDisposable.isDisposed()){
            compositeDisposable.clear();
        }
    }
}
