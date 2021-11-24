package com.my.rxjava.main;

import com.my.rxjava.ObservableImpl;
import com.my.rxjava.ObserverImpl;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava.main
 * @ClassName: Test
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/16 18:57
 */
public class Test01 {
    public static void main(String[] args) {
        ObservableImpl observableImpl = new ObservableImpl();

        ObserverImpl observer1 = new ObserverImpl();
        ObserverImpl observer2 = new ObserverImpl();
        ObserverImpl observer3 = new ObserverImpl();
        ObserverImpl observer4 = new ObserverImpl();

        observableImpl.registerObserver(observer1);
        observableImpl.registerObserver(observer2);
        observableImpl.registerObserver(observer3);
        observableImpl.registerObserver(observer4);

        observableImpl.notifyObservers();
        // =========================================================================================
        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                System.out.println(">>> 发射开始");
                emitter.onNext("next 发送数据");
                emitter.onComplete();
                System.out.println(">>> 发射完成");
//                emitter.onError(new Throwable("错误了"));
            }
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Disposable >>> 我订阅即调用");
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("onNext >>> " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError >>> " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete >>> 结束");
            }
        });


        final Disposable ss = Observable.create((ObservableOnSubscribe<String>)
                emitter ->
                        emitter.onNext("丽枫酒店老顾客管理科")).subscribe(
                                s ->
                                        System.out.println("accept >>> " + s));
        

        // =========================================================================================
        // TODO 上游
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                for (int i = 0; i < 100; i++) {
                    emitter.onNext(i);
                    System.out.println("上游发送 >>> " + i);
                }
                emitter.onComplete();
            }
        });
        // TODO 下游
        Observer observer = new Observer<Integer>(){

            private Disposable disposables;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                this.disposables = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("下游接收 >>> " + integer);

                if(integer > 50){
                    // TODO 切断下游   上游还在继续发
                    disposables.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        // TODO 订阅
        observable.subscribe(observer);
    }
}
