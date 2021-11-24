package com.my.rxjava;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava
 * @ClassName: ObservableImpl
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/16 18:51
 */
public class ObservableImpl implements Observable{
    private static final List<Observer> observers = new ArrayList<Observer>();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer1 : observers) {
            observer1.onChange("被观察者 发生了改变......");
        }
    }
}
