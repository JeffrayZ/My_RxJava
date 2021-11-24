package com.my.rxjava;

/**
 * 被观察者
 */
public interface Observable {

    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers();
}
