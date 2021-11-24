package com.my.rxjava.schedule;

import com.my.rxjava.ObservableOnSubscribe;
import com.my.rxjava.Observer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ProjectName: RxJava_Lib
 * @Package: com.my.rxjava.schedule
 * @ClassName: SubscribeOnIO
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2021/11/23 18:51
 */
public class SubscribeOnIO<T> implements ObservableOnSubscribe<T> {
    //    private static final Executor executor = Executors.newCachedThreadPool();
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    private ObservableOnSubscribe<T> source;

    public SubscribeOnIO(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    public void subscribe(Observer<? super T> observerEmitter) throws Exception {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    source.subscribe(observerEmitter);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    observerEmitter.onError(e);
//                }
//            }
//        });

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // todo 这里没有替换发射器 只是将发射器代码放在线程里运行
                    source.subscribe(observerEmitter);
                } catch (Exception e) {
                    e.printStackTrace();
                    observerEmitter.onError(e);
                }
            }
        });
    }
}
