package com.tencent.jrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RXJAVA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1.基本用法
        /*Observable.create(new ObservableOnSubscribe<Integer>() {// 第一步：初始化Observable
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable emit 1" + "\n");
                e.onNext(1);
                Log.e(TAG, "Observable emit 2" + "\n");
                e.onNext(2);
                Log.e(TAG, "Observable emit 3" + "\n");
                e.onNext(3);
                Log.e(TAG, "Observable emit 4" + "\n");
                e.onNext(4);

                e.onComplete();
            }
        })
//                .subscribeOn()  //用于指定 subscribe() 时所发生的线程
//                .observeOn()  //用于指定下游 Observer 回调发生的线程。
                .subscribe(new Observer<Integer>() { // 第三步：订阅

            // 第二步：初始化Observer
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e(TAG, "onSubscribe");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "onNext" + integer + "\n");
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，
                    // 让Observer观察者不再接收上游事件
                    mDisposable.dispose();
                }

            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete" + "\n");
            }
        }
//Consumer 即消费者，用于接收单个值
//                new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        Log.e(TAG, "accept" +integer+ "\n");
//                    }
//
//
//                }


        );*/


        /**
         * 线程切换需要注意的

         RxJava 内置的线程调度器的确可以让我们的线程切换得心应手，但其中也有些需要注意的地方。

         简单地说，subscribeOn() 指定的就是发射事件的线程，observerOn 指定的就是订阅者接收事件的线程。
         多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
         但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。

         作者：nanchen2251
         链接：https://www.jianshu.com/p/0cd258eecf60
         來源：简书
         简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
         */

        Disposable subscribe = Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
//                .map(new )
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "After observeOn(io)，Current thread is " + Thread.currentThread().getName());
                    }
                });

        /**
         * RxJava 中，已经内置了很多线程选项供我们选择，例如有：

         Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作；
         Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作；
         Schedulers.newThread() 代表一个常规的新线程；
         AndroidSchedulers.mainThread() 代表Android的主线程

         作者：nanchen2251
         链接：https://www.jianshu.com/p/0cd258eecf60
         來源：简书
         简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
         */

    }
}
