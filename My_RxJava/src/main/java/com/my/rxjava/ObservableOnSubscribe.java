package com.my.rxjava;


/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava
 * @ClassName: ObservableOnSubscribe
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/22 16:34
 */
public interface ObservableOnSubscribe<T> {
    void subscribe(Observer<? super T> observerEmitter) throws Exception;
}
