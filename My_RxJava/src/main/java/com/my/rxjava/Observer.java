package com.my.rxjava;

/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava
 * @ClassName: Observer
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/22 16:37
 */
public interface Observer<T> {
    void onSubscribe();


    void onNext(T t);


    void onError(Throwable e);


    void onComplete();
}
