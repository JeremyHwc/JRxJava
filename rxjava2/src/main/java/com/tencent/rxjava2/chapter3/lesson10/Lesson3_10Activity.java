package com.tencent.rxjava2.chapter3.lesson10;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.tencent.rxjava2.R;
import com.tencent.rxjava1.entity.Apple;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class Lesson3_10Activity extends AppCompatActivity {

    private List<Apple> mApples;

    {
        mApples = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            mApples.add(new Apple());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson3_10);
        findViewById(R.id.async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.
                        create(new ObservableOnSubscribe<Apple>() {
                            @Override
                            public void subscribe(ObservableEmitter<Apple> e) throws Exception {
                                if (!e.isDisposed()) {
                                    for (Apple tApple : mApples) {
                                        e.onNext(tApple);
                                    }
                                }
                            }
                        }).
                        subscribe(new Observer<Apple>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(Apple value) {
                                System.out.println(value);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
    }
}
