package com.my.rxjava.schedule;

import android.os.Handler;
import android.os.Looper;

import com.my.rxjava.ObservableOnSubscribe;
import com.my.rxjava.Observer;

/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava.schedule
 * @ClassName: ObserverOnMain
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/24 10:46
 */
public class ObserverOnMain<T> implements ObservableOnSubscribe<T> {

    private ObservableOnSubscribe<T> source;
    private static final Handler handler = new Handler(Looper.getMainLooper());

    public ObserverOnMain(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(Observer<? super T> observerEmitter) throws Exception {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    source.subscribe(observerEmitter);
                } catch (Exception e) {
                    e.printStackTrace();
                    observerEmitter.onError(e);
                }
            }
        });
    }
}
