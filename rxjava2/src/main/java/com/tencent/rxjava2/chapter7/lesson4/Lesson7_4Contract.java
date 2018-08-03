package com.tencent.rxjava2.chapter7.lesson4;

import com.tencent.rxjava1.chapter7.lesson2.BasePresenter;
import com.tencent.rxjava1.chapter7.lesson2.BaseView;

/**
 * com.tencent.rxjava1.chapter7.lesson4.Lesson7_4Contract
 *
 * @author SXDSF
 * @date 2017/12/5 下午2:36
 * @desc 契约类，描述MVP结构
 */

public interface Lesson7_4Contract {

    interface View extends BaseView<Presenter> {

        void update(String text);
    }

    abstract class Presenter extends BasePresenter<View> {
        public Presenter(View view) {
            super(view);
        }

        abstract void loadData();
    }
}
