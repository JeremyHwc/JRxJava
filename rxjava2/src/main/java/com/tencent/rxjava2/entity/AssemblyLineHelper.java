package com.tencent.rxjava2.entity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * com.tencent.rxjava2.entity.AssemblyLineHelper
 *
 * @author SXDSF
 * @date 2017/12/4 下午4:32
 * @desc 流水线帮助类
 */

public class AssemblyLineHelper {

    /**
     * 削皮
     */
    public static List<AppleWithoutSkin> peel(List<Apple> apples) {
        List<AppleWithoutSkin> afterPeel = new ArrayList<>();
        for (int i = 0; i < apples.size(); i++) {
            afterPeel.add(new AppleWithoutSkin());
        }
        return afterPeel;
    }

    /**
     * 榨汁
     */
    public static AppleJuice juice(List<AppleWithoutSkin> apples) {
        return new AppleJuice();
    }

    /**
     * 灌装
     */
    public static List<ABottomOfAppleJuice> fill(AppleJuice appleJuice) {
        List<ABottomOfAppleJuice> apples = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            apples.add(new ABottomOfAppleJuice());
        }
        return apples;
    }

    /**
     * 装箱
     */
    public static List<ABoxOfAppleJuice> pack(List<ABottomOfAppleJuice> apples) {
        List<ABoxOfAppleJuice> afterPack = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ABoxOfAppleJuice aBoxOfAppleJuice = new ABoxOfAppleJuice();
            if (i == 1) {
                aBoxOfAppleJuice.mRetained = true;
            }
            afterPack.add(aBoxOfAppleJuice);
        }
        return afterPack;
    }

    /**
     * 发货
     */
    public static Observable<ABoxOfAppleJuice> deliver(List<ABoxOfAppleJuice> apples) {
        return Observable.fromIterable(apples);
    }

    /**
     * 留下自用的
     */
    public static boolean retain(ABoxOfAppleJuice aBoxOfAppleJuice) {
        return !aBoxOfAppleJuice.mRetained;
    }
}
