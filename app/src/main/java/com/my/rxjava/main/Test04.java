package com.my.rxjava.main;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Predicate;

// TODO 过滤操作符学习
public class Test04 {
    public static void main(String[] args) {
        Observable.just("A", "B", "C")
                // TODO filter 过滤型操作符
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Throwable {
                        // true  不过滤  输出
                        // false 过滤  不输出
                        if (s.equals("A")) { // 过滤了 A 输出 B C
                            return false;
                        }
                        return true;
                    }
                })
                .subscribe(s -> System.out.println("过滤后 >>> " + s));

        System.out.println("");

        // =========================================================================================
        Observable
                // TODO interval 定时器
                .interval(2, TimeUnit.SECONDS)
                // TODO take 执行次数达到8  停止定时器
                .take(8)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Throwable {
                        System.out.println(System.currentTimeMillis() + " >>> " + aLong);
                    }
                });
        System.out.println("");

        // =========================================================================================
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                emitter.onNext(1);
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onNext(4);
                emitter.onNext(4);
            }
        })
                // TODO 过滤重复发送事件
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        // 输出 1 2 3 4
                        System.out.println(integer);
                    }
                });
        System.out.println("");

        // =========================================================================================
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("你好");
                emitter.onNext("我是");
                emitter.onNext("你爸爸");
            }
        })
                // TODO 输出指定下标的元素  没找到就输出默认值
                .elementAt(2, "我是默认值")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        System.out.println(s);
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
