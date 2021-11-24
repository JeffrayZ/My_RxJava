package com.my.rxjava.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

// TODO 背压模式 学习
public class Test09 {
    public static void main(String[] args) {
    // todo 生产 大于 消费
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Throwable {
                for (int i = 0; i < 32; i++) {
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        },
                BackpressureStrategy.ERROR // 缓存池满了直接报错
//                BackpressureStrategy.BUFFER // 缓存池满了，等待下游处理
//                BackpressureStrategy.DROP // 缓存池满了，后面的发送事件会被丢弃
//                BackpressureStrategy.LATEST // 只存储128个事件
//                BackpressureStrategy.MISSING
        ).subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                // 只请求 5次事件给下游输出
                // 不请求 下游收不到事件
                s.request(5);
            }

            @Override
            public void onNext(Integer integer) {
                // onError >>> create: could not emit value due to lack of requests
                // 说明还有事件没有处理
                System.out.println("onNext >>> " + integer);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("onError >>> "+t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });


//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    char c = 1;
//                }
//            }
//        });
//        thread.start();

    }
}
