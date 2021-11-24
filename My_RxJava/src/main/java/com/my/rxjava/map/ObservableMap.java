package com.my.rxjava.map;

import com.my.rxjava.Function;
import com.my.rxjava.ObservableOnSubscribe;
import com.my.rxjava.Observer;

/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava
 * @ClassName: ObservableMap
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/23 11:19
 */
public class ObservableMap<T,R> implements ObservableOnSubscribe<R> {

    // todo 这里代表上一层的能力 从头到尾 source只有一个，变换的是 source对应的Observer >>> source.subscribe(observer);
    private ObservableOnSubscribe<T> source;
    // todo 这里代表下一层的能力 也就是 >>> 原始发射器
    private Observer<? super R> observerEmitter;
    private Function<? super T, ? extends R> function;

    public ObservableMap(ObservableOnSubscribe source, Function<? super T, ? extends R> function) {
        this.source = source;
        this.function = function;
    }

    @Override
    public void subscribe(Observer<? super R> observerEmitter) throws Exception {
        // todo 这里代表下一层的能力 也就是Test.java里面的 new Observer<String>() {...}   我叫它 >>> 原始发射器
        this.observerEmitter = observerEmitter;

        ObserverMap<T,R> observerMapEmitter = new ObserverMap(observerEmitter, source, function);
        // todo 在这里将 source已经订阅的Observer做了替换
        // todo 替换成 ObserverMap 替换了发射器  我叫它 map发射器
        source.subscribe(observerMapEmitter);
    }

    /**
     * map层需要做的事情
     * @param <T>
     */
    class ObserverMap<T,R> implements Observer<T> {
        private ObservableOnSubscribe<T> source;
        // todo 原始发射器
        private Observer<? super R> observerEmitter;
        private Function<? super T, ? extends R> function;

        public ObserverMap(Observer<? super R> observerEmitter,
                           ObservableOnSubscribe<T> source,
                           Function<? super T, ? extends R> function) {
            this.observerEmitter = observerEmitter;
            this.function = function;
            this.source = source;
        }

        @Override
        public void onSubscribe() {
            observerEmitter.onSubscribe();
        }

        @Override
        public void onNext(T t) {
            try {
                // todo 做了一次转化，observerEmitter本来发射的是 T类型  现在是 R类型
                R next = function.apply(t);
                // todo 原始发射器 执行 onNext方法
                observerEmitter.onNext(next);
            } catch (Exception e) {
                e.printStackTrace();
                observerEmitter.onError(e);
            }
        }

        @Override
        public void onError(Throwable e) {
            observerEmitter.onError(e);
        }

        @Override
        public void onComplete() {
            observerEmitter.onComplete();
        }
    }
}
