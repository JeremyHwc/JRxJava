package com.tencent.rxjava2.chapter7.lesson4;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * com.tencent.rxjava2.chapter7.lesson4.Lesson7_4Presenter
 *
 * @author SXDSF
 * @date 2017/12/5 下午2:37
 * @desc Presenter
 */

public class Lesson7_4Presenter extends Lesson7_4Contract.Presenter {

    private ObservableEmitter<Object> mEmitter;

    private AtomicInteger mAtomicInteger = new AtomicInteger(0);

    public Lesson7_4Presenter(Lesson7_4Contract.View view) {
        super(view);
    }

    @Override
    void loadData() {
        if (mEmitter == null) {
            Observable.
                    create(new ObservableOnSubscribe<Object>() {
                        @Override
                        public void subscribe(ObservableEmitter<Object> e) throws Exception {
                            mEmitter = e;
                        }
                    }).
                    throttleFirst(1500, TimeUnit.MILLISECONDS).
                    subscribe(new Consumer<Object>() {
                        @Override
                        public void accept(Object s) throws Exception {
                            mView.update("就不让疯狂点击" + System.currentTimeMillis());
                        }
                    });

            Observable.
                    create(new ObservableOnSubscribe<Object>() {
                        @Override
                        public void subscribe(ObservableEmitter<Object> e) throws Exception {
                            mEmitter = e;
                        }
                    }).
                    switchMap(new Function<Object, ObservableSource<Integer>>() {
                        @Override
                        public ObservableSource<Integer> apply(Object o) throws Exception {
                            return NetworkService.network();
                        }
                    }).
                    subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer i) throws Exception {
                            mView.update("当前的计数是:" + i);
                        }
                    });
        }

        if (!mEmitter.isDisposed()) {
            mEmitter.onNext("");
        }
    }
}
