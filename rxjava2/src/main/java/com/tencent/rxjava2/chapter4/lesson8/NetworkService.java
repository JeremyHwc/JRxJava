package com.tencent.rxjava2.chapter4.lesson8;

import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * com.tencent.rxjava1.chapter4.lesson8.NetworkService
 *
 * @author SXDSF
 * @date 2017/12/5 上午10:25
 * @desc 模拟网络请求
 */

public class NetworkService {

    public static AtomicInteger sAtomicInteger = new AtomicInteger(0);

    public static Observable<Integer> network() {
        return Observable.
                create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                        int tCurrent = sAtomicInteger.getAndIncrement();
                        try {
                            Thread.sleep(2000);
                        } catch (Exception ex) {

                        }
                        if (!e.isDisposed()) {
                            e.onNext(tCurrent);
                            e.onComplete();
                        }
                    }
                }).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }
}
