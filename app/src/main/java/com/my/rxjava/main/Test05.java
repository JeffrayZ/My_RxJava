package com.my.rxjava.main;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;

// TODO 条件型操作符学习
public class Test05 {
    public static void main(String[] args) {

        // TODO 需求：只要给定的数据集里面包含 C，那么就返回 false
        Observable.just("A", "B", "C")
                // TODO all 条件型操作符
                .all(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        if(s.equalsIgnoreCase("C")){
                            return false;
                        }
                        return true;
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Throwable {
                        System.out.println("all >>> " + aBoolean);
                    }
                });

        System.out.println("");
        // ===========================================================================================
        Observable.just("Android","IOS","Java","H5")
                // TODO contains 操作符
                .contains("IOS")
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean s) throws Throwable {
                        System.out.println("contains >>> " + s);
                    }
                });
        System.out.println("");
        // ===========================================================================================
        Observable.just("Android","IOS","Java","H5")
                // TODO isEmpty 操作符
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean s) throws Throwable {
                        System.out.println("isEmpty >>> " + s);
                    }
                });
        System.out.println("");
        // ===========================================================================================
        Observable.just("Android","IOS","Java","H5")
                // TODO any 操作符
                .any(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        // 全部是false 才是false   只要有一个是true   那就是true
                        return s.equalsIgnoreCase("IOS");
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean s) throws Throwable {
                        System.out.println("any >>> " + s);
                    }
                });

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    char c = 1;
                }
            }
        });
        thread.start();*/
    }
}
