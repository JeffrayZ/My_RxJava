package com.my.rxjava.main;

import com.my.rxjava.Function;
import com.my.rxjava.Observable;
import com.my.rxjava.ObservableOnSubscribe;
import com.my.rxjava.Observer;

/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava.main
 * @ClassName: Test
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/22 16:47
 */
public class Test {
    public static void main(String[] args) {
        /*Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(Observer<? super Integer> observerEmitter) throws Exception {
                observerEmitter.onNext(111);
                observerEmitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe() {
                System.out.println("onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext >>> " + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError >>> " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete");
            }
        });


        Observable.just(121).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe() {
                System.out.println("just onSubscribe");
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("just onNext >>> " + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("just onError >>> " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("just onComplete");
            }
        });


        Observable.just("A", "B", "C", "D", "E").subscribe(new Observer<String>() {
            @Override
            public void onSubscribe() {
                System.out.println("just onSubscribe");
            }

            @Override
            public void onNext(String integer) {
                System.out.println("just onNext >>> " + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("just onError >>> " + e);
            }

            @Override
            public void onComplete() {
                System.out.println("just onComplete");
            }
        });*/

        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(Observer<? super Integer> observerEmitter) throws Exception {
                // todo observerEmitter = {ObservableMap$ObserverMap@820}
                // todo 因为source的Observer做了替换，所以再执行 onNext onComplete就是执行的 ObserverMap里面的对应方法
                observerEmitter.onNext(12306); // 这里面执行 apply方法
                observerEmitter.onComplete();
            }
        })
                .map(new Function<Integer, String>() {

                    // todo apply方法是执行在 ObservableMap onNext方法里面的
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "map变换：" + integer;
                    }
                })
                .map(new Function<String, StringBuffer>() {

                    @Override
                    public StringBuffer apply(String s) throws Exception {
                        return new StringBuffer(s).append("==============");
                    }
                })
                .subscribe(new Observer<StringBuffer>() {
                    @Override
                    public void onSubscribe() {
                        System.out.println("map onSubscribe");
                    }

                    @Override
                    public void onNext(StringBuffer result) {
                        System.out.println("map onNext >>> " + result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("map onError >>> " + e);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("map onComplete");
                    }
                });


        /*Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(Observer<? super Integer> observerEmitter) throws Exception {
                System.out.println("subscribe >>> " + Thread.currentThread().getName());
                observerEmitter.onNext(879589);
                observerEmitter.onComplete();
            }
        })
                .subscribeOnIO()
                .subscribe(new Observer<Integer>() {

            @Override
            public void onSubscribe() {
                System.out.println("onSubscribe >>> " + Thread.currentThread().getName());
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("onNext >>> " + Thread.currentThread().getName());
                System.out.println("onNext >>> " + integer);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError >>> " + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete >>> " + Thread.currentThread().getName());
            }
        });*/
    }
}
