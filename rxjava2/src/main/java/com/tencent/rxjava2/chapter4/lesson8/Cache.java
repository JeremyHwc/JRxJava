package com.tencent.rxjava2.chapter4.lesson8;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * com.tencent.rxjava1.chapter4.lesson8.Cache
 *
 * @author SXDSF
 * @date 2017/12/5 上午10:45
 * @desc 三级缓存
 */

public class Cache {

    private String memory = "null";
    private String disk = "null";

    public void start() {
        Observable.
                concat(memory(), disk(), network()).
                filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s != null && !"".equals(s) && !"null".equals(s);
                    }
                }).
                firstElement().
                subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("走三级缓存拿到的数据是" + s);
                    }
                });
    }

    /**
     * 网络拿数据
     *
     * @return
     */
    private Observable<String> network() {
        return Observable.
                create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("data");
                        e.onComplete();
                    }
                }).
                doOnNext(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {
                        if (s != null && !"".equals(s) && !"null".equals(s)) {
                            System.out.println("在走网络拿数据的时候，在拿到数据时存到内存");
                            disk = s;
                        }
                    }
                }).
                compose(log("network"));
    }

    /**
     * 运存拿数据
     *
     * @return
     */
    private Observable<String> memory() {
        return Observable.
                create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext(memory);
                        e.onComplete();
                    }
                }).
                compose(log("memory"));
    }

    /**
     * 内存拿数据
     *
     * @return
     */
    private Observable<String> disk() {
        return Observable.
                create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext(disk);
                        e.onComplete();
                    }
                }).
                doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s != null && !"".equals(s) && !"null".equals(s)) {
                            System.out.println("在走内存拿数据的时候，在拿到数据时存到运存");
                            memory = s;
                        }
                    }
                }).
                compose(log("disk"));
    }

    private ObservableTransformer<String, String> log(final String from) {
        return new ObservableTransformer<String, String>() {
            @Override
            public ObservableSource<String> apply(Observable<String> upstream) {
                return upstream.
                        doOnNext(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                System.out.println("记下一条日志，来源是" + from + "数据是" + s);
                            }
                        });
            }
        };
    }
}
