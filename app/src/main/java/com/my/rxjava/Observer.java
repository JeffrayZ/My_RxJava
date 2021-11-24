package com.my.rxjava;

/**
 * 观察者
 */
public interface Observer {
    <T> void onChange(T info);
}
