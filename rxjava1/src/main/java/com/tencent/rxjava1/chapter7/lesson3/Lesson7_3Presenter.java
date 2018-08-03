package com.tencent.rxjava1.chapter7.lesson3;

import com.google.gson.Gson;
import com.tencent.rxjava1.chapter7.lesson1.NetworkService;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * com.tencent.rxjava1.chapter7.lesson3.Lesson7_3Presenter
 *
 * @author SXDSF
 * @date 2017/12/4 下午11:56
 * @desc Presenter实现类
 */

public class Lesson7_3Presenter extends Lesson7_3Contract.Presenter {

    public Lesson7_3Presenter(Lesson7_3Contract.View view) {
        super(view);
    }

    @Override
    void loadData() {
        NetworkService.getInterface().pre().
                subscribeOn(Schedulers.io()).
                map(new Function<String, Info>() {
                    @Override
                    public Info apply(String s) throws Exception {
                        return new Gson().fromJson(s, Info.class);
                    }
                }).
                flatMap(new Function<Info, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Info info) throws Exception {
                        return NetworkService.getInterface().doSomething(info.name);
                    }
                }).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        mView.update(value + System.currentTimeMillis());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
