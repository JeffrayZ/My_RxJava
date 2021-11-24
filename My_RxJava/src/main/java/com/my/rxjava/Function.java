package com.my.rxjava;

/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava
 * @ClassName: Function
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/23 10:56
 */
public interface Function<T,R> {
    R apply(T t) throws Exception;
}
