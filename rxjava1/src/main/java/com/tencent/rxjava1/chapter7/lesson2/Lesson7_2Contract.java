package com.tencent.rxjava1.chapter7.lesson2;

/**
 * com.tencent.rxjava1.chapter7.lesson2.Lesson7_2Contract
 *
 * @author SXDSF
 * @date 2017/12/3 下午10:21
 * @desc 契约接口，描述MVP结构
 */

public interface Lesson7_2Contract {

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
