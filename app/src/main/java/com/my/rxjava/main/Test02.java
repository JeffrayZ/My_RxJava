package com.my.rxjava.main;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

// TODO 操作符学习
public class Test02 {
    public static void main(String[] args) {
        // TODO creat 操作符
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                emitter.onNext("A");
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("create >>> " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
        // =========================================================================================
        // TODO just操作符 自动发送
        Observable.just("A", "B").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("just >>> " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        // =========================================================================================
        // TODO fromArray 操作符 自动发送 数组级别
        String[] strings = {"A", "B", "C"};
        Observable.fromArray(strings).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("fromArray >>> " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        // =========================================================================================
        // TODO empty 操作符 自动发送
        Observable.empty().subscribe(new Observer<Object>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {
                // 没有事件在这里接收，这个方法不会执行
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                // 内部一定会调用 onComplete
                System.out.println("empty >>> onComplete");
            }
        });
        // =========================================================================================
        // TODO range 操作符 自动发送
        Observable.range(1, 5).subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@androidx.annotation.NonNull Integer i) {
                System.out.println("range >>> " + i);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("range >>> onComplete");
            }
        });
    }
}
