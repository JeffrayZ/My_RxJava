package com.my.rxjava.main;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function4;

// TODO 合并型操作符学习
public class Test06 {
    public static void main(String[] args) {
        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onComplete();
                    }
                })
                // todo startWith 先执行startWith里面的  在执行外面的
                .startWith(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                        emitter.onNext(10);
                        emitter.onNext(20);
                        emitter.onNext(30);
                        // 这个必须要加上，不然上面的不执行
                        emitter.onComplete();
                    }
                }))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        System.out.println("startWith >>> " + integer);
                    }
                });

        System.out.println("");
        // =========================================================================================

        Observable
                .create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onComplete();
                    }
                })
                // todo concatWith 先执行外面的  在执行concatWith里面的
                // 与 startWith相反
                .concatWith(Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                        emitter.onNext(10);
                        emitter.onNext(20);
                        emitter.onNext(30);
                        // 这个必须要加上，不然上面的不执行
                        emitter.onComplete();
                    }
                }))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        System.out.println("concatWith >>> " + integer);
                    }
                });

        System.out.println("");
        // =========================================================================================

        Observable
                // todo concat 最多合并4个被观察者 顺序执行
                .concat(
                        Observable.just(1),
                        Observable.just(2),
                        Observable.just(3),
                        Observable.create(new ObservableOnSubscribe<Integer>() {

                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                                emitter.onNext(78);
                                emitter.onComplete();
                            }
                        }))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        System.out.println("concat >>> " + integer);
                    }
                });

        System.out.println("");
        // =========================================================================================

        Observable
                // todo merge 最多合并4个被观察者 并列执行
                .merge(
                        Observable.just(1),
                        Observable.just(2),
                        Observable.just(3),
                        Observable.create(new ObservableOnSubscribe<Integer>() {

                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                                emitter.onNext(78);
                                emitter.onComplete();
                            }
                        }))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        System.out.println("merge >>> " + integer);
                    }
                });

        System.out.println("");
        // =========================================================================================

        Observable
                // todo zip 最多合并9个被观察者
                .zip(
                        Observable.just(1),
                        Observable.just("ABC"),
                        Observable.just(3),
                        Observable.create(new ObservableOnSubscribe<Integer>() {

                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                                emitter.onNext(78);
                                emitter.onComplete();
                            }
                        }),
                        new Function4<Integer, String, Integer, Integer, String>() {
                            @Override
                            public String apply(Integer integer, String s, Integer integer3, Integer integer4) throws Throwable {
                                return "最终数据：" + (integer + integer3 + integer4) + "::" + s;
                            }
                        })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String integer) throws Throwable {
                        System.out.println("zip >>> " + integer);
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
