package com.tencent.rxjava1.chapter4.lesson8;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.tencent.rxjava1.R;
import com.tencent.rxjava1.entity.ABottomOfAppleJuice;
import com.tencent.rxjava1.entity.ABoxOfAppleJuice;
import com.tencent.rxjava1.entity.Apple;
import com.tencent.rxjava1.entity.AppleJuice;
import com.tencent.rxjava1.entity.AppleWithoutSkin;
import com.tencent.rxjava1.entity.AssemblyLineHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

import static com.tencent.rxjava1.entity.AssemblyLineHelper.deliver;
import static com.tencent.rxjava1.entity.AssemblyLineHelper.fill;
import static com.tencent.rxjava1.entity.AssemblyLineHelper.juice;
import static com.tencent.rxjava1.entity.AssemblyLineHelper.pack;
import static com.tencent.rxjava1.entity.AssemblyLineHelper.retain;


public class Lesson4_8Activity extends AppCompatActivity {

    private List<Apple> mApples;

    {
        mApples = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            mApples.add(new Apple());
        }
    }

    private Cache mCache = new Cache();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson4_8);
        findViewById(R.id.async).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Observable.just(mApples).
                        map(new Function<List<Apple>, List<AppleWithoutSkin>>() {
                            @Override
                            public List<AppleWithoutSkin> apply(List<Apple> apples) throws Exception {
                                System.out.println("削皮");
                                return AssemblyLineHelper.peel(apples);
                            }

                        }).
                        map(new Function<List<AppleWithoutSkin>, AppleJuice>() {

                            @Override
                            public AppleJuice apply(List<AppleWithoutSkin> appleWithoutSkins) throws Exception {
                                System.out.println("榨汁");
                                return juice(appleWithoutSkins);
                            }
                        }).
                        map(new Function<AppleJuice, List<ABottomOfAppleJuice>>() {
                            @Override
                            public List<ABottomOfAppleJuice> apply(AppleJuice appleJuice) throws Exception {
                                System.out.println("装瓶");
                                return fill(appleJuice);
                            }
                        }).
                        map(new Function<List<ABottomOfAppleJuice>, List<ABoxOfAppleJuice>>() {
                            @Override
                            public List<ABoxOfAppleJuice> apply(List<ABottomOfAppleJuice> aBottomOfAppleJuices) throws Exception {
                                System.out.println("装箱");
                                return pack(aBottomOfAppleJuices);
                            }
                        }).
                        flatMap(new Function<List<ABoxOfAppleJuice>, Observable<ABoxOfAppleJuice>>() {
                            @Override
                            public Observable<ABoxOfAppleJuice> apply(List<ABoxOfAppleJuice> aBoxOfAppleJuices) throws Exception {
                                // 分箱发货给经销商
                                System.out.println("发货");
                                return deliver(aBoxOfAppleJuices);
                            }

                        }).
                        filter(new Predicate<ABoxOfAppleJuice>() {
                            @Override
                            public boolean test(ABoxOfAppleJuice aBoxOfAppleJuice) throws Exception {
                                System.out.println("是否留下自用" + aBoxOfAppleJuice.mRetained);
                                return retain(aBoxOfAppleJuice);
                            }
                        }).
                        subscribe(new Consumer<ABoxOfAppleJuice>() {
                            @Override
                            public void accept(ABoxOfAppleJuice aBoxOfAppleJuice) throws Exception {
                                System.out.println("卖出了" + aBoxOfAppleJuice);
                            }
                        });
            }
        });

        RxView.
                clicks(findViewById(R.id.shake)).
//                throttleFirst(1500, TimeUnit.MILLISECONDS).
                switchMap(new Function<Object, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(Object o) throws Exception {
                        return NetworkService.network();
                    }
                }).
                subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer i) throws Exception {
                        System.out.println("就不让你疯狂连续点击" + i);
                    }
                });

        RxView.
                clicks(findViewById(R.id.cache)).
                subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mCache.start();
                    }
                });
    }
}
