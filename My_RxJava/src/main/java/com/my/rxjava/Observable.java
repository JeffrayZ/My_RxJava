package com.my.rxjava;

import com.my.rxjava.map.ObservableMap;
import com.my.rxjava.schedule.ObserverOnMain;
import com.my.rxjava.schedule.SubscribeOnIO;

/**
 * @param <T> 类泛型
 */
public class Observable<T> {

    ObservableOnSubscribe source;

    private Observable(ObservableOnSubscribe source) {
        this.source = source;
    }

    /**
     * 手写create操作符
     *
     * @param source
     * @param <T>    静态方法声明的泛型
     * @return
     */
    public static <T> Observable<T> create(ObservableOnSubscribe<? extends T> source) {
        return new Observable<>(source);
    }

    /**
     * 手写just操作符
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Observable<T> just(T... t) {
        return new Observable<T>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(Observer<? super T> observerEmitter) throws Exception {
                for (T t1 : t) {
                    // 循环发送事件
                    observerEmitter.onNext(t1);
                }
                // 一定会调用完成
                observerEmitter.onComplete();
            }
        });
    }

    /**
     * 手写just操作符  两个参数
     * @param t1 t2
     * @param <T>
     * @return
     */
    public static <T> Observable<T> just(T t1,T t2) {
        return new Observable<T>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(Observer<? super T> observerEmitter) throws Exception {
                // 发送事件
                observerEmitter.onNext(t1);
                observerEmitter.onNext(t2);
                // 一定会调用完成
                observerEmitter.onComplete();
            }
        });
    }

    /**
     * 手写just操作符  一个参数
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Observable<T> just(T t) {
        return new Observable<T>(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(Observer<? super T> observerEmitter) throws Exception {
                // 发送事件
                observerEmitter.onNext(t);
                // 一定会调用完成
                observerEmitter.onComplete();
            }
        });
    }

    /***
     * ? super T 表示可写
     * ? extends R 表示可读
     * @param function
     * @param <R>
     * @return
     */
    public <R> Observable<R> map(Function<? super T,? extends R> function) {
        // map层需要的能力
        ObservableMap<T,R> observableMapSource = new ObservableMap(this.source,function);
        return new Observable<R>(observableMapSource);
    }

    public <T> Observable<T> subscribeOnIO() {
        return new Observable<T>(new SubscribeOnIO(this.source));
    }

    public <T> Observable<T> observerOnMain() {
        return new Observable<T>(new ObserverOnMain(this.source));
    }

    /**
     * todo 在这里 传入的是外面的 Observer
     * @param observer
     */
    public void subscribe(Observer<? extends T> observer) {
        observer.onSubscribe();
        try {
            // todo 但是这里将 observer作为发射器 ‘observerEmitter’使用的 实质上是同一个对象 所以才能传递数据
            source.subscribe(observer);
        } catch (Exception e) {
            e.printStackTrace();
            observer.onError(e);
        }
    }
}
