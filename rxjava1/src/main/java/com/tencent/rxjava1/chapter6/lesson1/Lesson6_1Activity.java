package com.tencent.rxjava1.chapter6.lesson1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tencent.rxjava1.R;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class Lesson6_1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson6_1);
        findViewById(R.id.btnRxJava1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable
                        .create(new Observable.OnSubscribe<String>() {
                            @Override
                            public void call(Subscriber<? super String> subscriber) {
                                if (!subscriber.isUnsubscribed()) {
                                    subscriber.onNext("test");
                                    System.out.println("currentThread:" + Thread.currentThread());
                                    subscriber.onCompleted();
                                }
                            }
                        })
                        .compose(new Observable.Transformer<String, String>() {
                            @Override
                            public Observable<String> call(Observable<String> stringObservable) {
                                return stringObservable.
                                        subscribeOn(Schedulers.newThread()).
                                        observeOn(AndroidSchedulers.mainThread());
                            }
                        })
                        .subscribe(new Observer<String>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(String s) {
                                System.out.println("currentThread:" + Thread.currentThread());
                                System.out.println("onNext:" + s);
                            }
                        });
            }
        });

    }
}
