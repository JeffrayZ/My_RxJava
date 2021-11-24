package com.my.rxjava.main;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

// TODO 线程切换学习
public class Test08 {
    public static void main(String[] args) {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                System.out.println("上游线程 >>> " + Thread.currentThread().getName());
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        })

                // todo 给上游分配线程
                // todo 给上游分配多次线程  只会在第一次生效
                .subscribeOn(Schedulers.computation())
                // todo 给下游分配线程
                // todo 给下游分配多次  每次都会切换线程
//                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.single())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {
                        System.out.println("accept >>> " + integer);
                        System.out.println("下游线程 >>> " + Thread.currentThread().getName());
                    }
                });

        // =========================================================================================

        // todo 加载网络图片到控件上
        Observable.just("https:hajflaksksjkljls.png")
                // todo 从网络地址转换成 Bitmap
                .map(new Function<String, Bitmap>() {
                    @Override
                    public Bitmap apply(String s) throws Throwable {
                        return Bitmap.createBitmap(-1, -1, null);
                    }
                })
                // todo 下载操作在io线程
                .subscribeOn(Schedulers.io())
                // todo UI操作在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // todo 展示加载对话框
                    }

                    @Override
                    public void onNext(@NonNull Bitmap bitmap) {
                        // todo 这里做图片展示
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // todo 加载失败
                    }

                    @Override
                    public void onComplete() {
                        // todo 加载结束 隐藏加载对话框
                    }
                });


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    char c = 1;
                }
            }
        });
        thread.start();

    }

    // 图片上绘制文字
    private Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }
}
