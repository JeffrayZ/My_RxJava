package com.my.rxjava;

/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava
 * @ClassName: ObserverImpl
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/16 18:56
 */
public class ObserverImpl implements Observer{
    @Override
    public <T> void onChange(T info) {
        System.out.println("onChange >>>> " + info);
    }
}
