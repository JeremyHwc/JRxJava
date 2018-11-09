package com.tencent.rxjava2.chapter7.lesson2;


import com.tencent.rxjava2.chapter7.lesson1.NetworkService;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * com.tencent.rxjava2.chapter7.lesson2.Lesson7_2Presenter
 *
 * @author SXDSF
 * @date 2017/12/3 下午10:25
 * @desc Presenter实现类
 */

public class Lesson7_2Presenter extends Lesson7_2Contract.Presenter {

    public Lesson7_2Presenter(Lesson7_2Contract.View view) {
        super(view);
    }

    @Override
    void loadData() {
        NetworkService
                .getInterface()
                .json()
                .compose(new NetworkService.NetworkTransformer<String>())
                .subscribe(new Observer<String>() {
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
