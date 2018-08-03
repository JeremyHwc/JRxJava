package com.tencent.rxjava1.chapter7.lesson2;

/**
 * com.tencent.rxjava1.chapter7.lesson2.BasePresenter
 *
 * @author SXDSF
 * @date 2017/12/3 下午10:17
 * @desc 基础的Presenter
 */

public abstract class BasePresenter<T extends BaseView> {

    protected final T mView;

    public BasePresenter(T view) {
        mView = view;
    }
}
