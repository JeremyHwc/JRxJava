package com.tencent.rxjava2.chapter7.lesson3;

import com.tencent.rxjava2.chapter7.lesson2.BasePresenter;
import com.tencent.rxjava2.chapter7.lesson2.BaseView;

/**
 * com.tencent.rxjava2.chapter7.lesson3.Lesson7_3Contract
 *
 * @author SXDSF
 * @date 2017/12/4 下午11:55
 * @desc 契约接口，描述MVP结构
 */

public interface Lesson7_3Contract {

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
