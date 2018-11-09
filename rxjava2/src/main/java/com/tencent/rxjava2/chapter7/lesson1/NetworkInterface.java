package com.tencent.rxjava2.chapter7.lesson1;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * com.tencent.rxjava2.chapter7.lesson1.NetworkInterface
 *
 * @author SXDSF
 * @date 2017/11/29 上午11:47
 * @desc 接口描述文件
 */

public interface NetworkInterface {

    @GET("/users/json")
    Observable<String> json();

    @GET("/users/pre")
    Observable<String> pre();

    @FormUrlEncoded
    @POST("/users/do")
    Observable<String> doSomething(@Field("name") String name);
}
