package com.my.rxjava.main;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;

// TODO 异常处理操作符学习
public class Test07 {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                for (int i = 0; i < 100; i++) {
                    if (i == 5) {
                        // RxJava这种写法不标准
//                        throw new IllegalStateException("5 出现异常");

                        // 这是 RxJava标准写法
                        emitter.onError(new IllegalStateException("5 出现异常"));
                    }
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        })
                // todo onErrorReturn 异常操作符
                // 发生异常 会中断后续发送操作 complete会执行
                .onErrorReturn(new Function<Throwable, Integer>() {
                    @Override
                    public Integer apply(Throwable throwable) throws Throwable {
                        // 这里相当于提前对异常进行处理~
                        System.out.println("onErrorReturn >>> " + throwable.getMessage());
                        // 自定义了400 代表有错误
                        return 400;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        // todo onErrorReturn处理后  400会在这里输出
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // todo onErrorReturn处理后  这里就不执行了
                        System.out.println("onError >>> " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        System.out.println("");
        // =========================================================================================

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                for (int i = 0; i < 100; i++) {
                    if (i == 5) {
                        emitter.onError(new IllegalStateException("5 出现异常"));
                    }
                    emitter.onNext(i);
                }
                emitter.onComplete();
            }
        })
                // todo onErrorResumeNext 返回的是被观察者 所以能继续发射一些事件
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
                    @Override
                    public ObservableSource<? extends Integer> apply(Throwable throwable) throws Throwable {
                        System.out.println("onErrorResumeNext >>> " + throwable.getMessage());
                        return Observable.create(new ObservableOnSubscribe<Integer>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                                // 被观察者再次发送
                                emitter.onNext(3333);
                                emitter.onNext(1111);
                                emitter.onNext(2222);
                                emitter.onComplete();
                            }
                        });
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError >>> " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        System.out.println("");
        // =========================================================================================

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                for (int i = 0; i < 100; i++) {
                    if (i == 5) {
                        emitter.onError(new IllegalStateException("5 出现异常"));
                    } else {
                        emitter.onNext(i);
                    }
                }
                emitter.onComplete();
            }
        })
                // todo retry 重试操作符 3表示重试 3次
                .retry(3,new Predicate<Throwable>() {
                    @Override
                    public boolean test(Throwable throwable) throws Throwable {
                        System.out.println("retry >>> " + throwable.getMessage());
                        // false 代表不重试  true 重试
                        return true;
                    }
                })
                /*.retry(new BiPredicate<Integer, Throwable>() {
                    @Override
                    public boolean test(@NonNull Integer integer, @NonNull Throwable throwable) throws Throwable {
                        System.out.println("retry >>> " + integer + "::"+throwable.getMessage());
                        // false 代表不重试  true 重试
                        return true;
                    }
                })*/
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("onError >>> " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
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
