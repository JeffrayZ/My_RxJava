package com.my.rxjava.main;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observables.GroupedObservable;

// TODO 中间变换操作符学习
public class Test03 {
    public static void main(String[] args) {
        int initial = 2;
        System.out.println("原始数据 >>> " + initial);
        Observable.just(initial)
                // TODO 中间变换操作符  这里 int 转 String
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Throwable {
                        return "map转换" + integer;
                    }
                })
                // TODO 中间变换操作符  这里 String 转 char[]
                .map(new Function<String, char[]>() {
                    @Override
                    public char[] apply(String s) throws Throwable {
                        return s.toCharArray();
                    }
                })
                .subscribe(new Observer<char[]>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        System.out.println("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull char[] s) {
                        System.out.println("map >>> " + Arrays.toString(s));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });

        System.out.println("");
        // =========================================================================================

        Observable.just(1111, 2222, 3333)
                // TODO flatMap 转换 无序
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Throwable {
                        // TODO Observable 可以再次发送的手动发送事件
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                                emitter.onNext("flatMap 转换 >>> " + integer);
                            }
                        });
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        System.out.println(System.currentTimeMillis() + " " + s);
                    }
                });

        System.out.println("");
        // =========================================================================================

        Observable.just(2, 1, 3)
                // TODO concatMap 转换 排序
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Throwable {
                        // TODO Observable 可以再次发送的手动发送事件
                        return Observable.create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                                emitter.onNext("concatMap 转换 >>> " + integer);
                            }
                        });
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Throwable {
                        System.out.println(System.currentTimeMillis() + " " + s);
                    }
                });

        System.out.println("");
        // =========================================================================================
        Observable.just(500, 1000, 2000, 8000, 10000, 30000, 100000)
                // TODO 分组变换操作符
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Throwable {
                        return integer >= 8000 ? "高端配置" : "普通配置";
                    }
                })
                // groupBy比较特殊，下游有标准的写法
                .subscribe(new Consumer<GroupedObservable<String, Integer>>() {
                    @Override
                    public void accept(GroupedObservable<String, Integer> objectIntegerGroupedObservable) throws Throwable {
                        // 高端配置   普通配置
                        System.out.println("key >>> " + objectIntegerGroupedObservable.getKey());
                        objectIntegerGroupedObservable.subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Throwable {
//                        内容 >>> 500
//                        内容 >>> 1000
//                        内容 >>> 2000

//                        内容 >>> 8000
//                        内容 >>> 10000
//                        内容 >>> 30000
//                        内容 >>> 100000
                                System.out.println("value >>> " + integer);
                            }
                        });
                    }
                });

        System.out.println("");
        // =========================================================================================
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                for (int i = 0; i < 100; i++) {
                    emitter.onNext(i);
                }
            }
        })
                // TODO 很多数据想分批次发送出去，先缓存到 buffer
                // 20 --- 表示一次发送20个，100会被分成5组
                .buffer(20)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> integers) throws Throwable {
                        System.out.println(integers);
                    }
                });
    }
}
