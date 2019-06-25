package cn.org.happyhome.nursing.utils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import cn.org.happyhome.library.BuildConfig;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public final class UploadUtils {

    private static UploadUtils uploadUtils;

    private static UploadManager uploadManager;


    public UploadListener uploadListener;

    public interface UploadListener {
        void showUploadResult(ArrayList<String> key);

        void showFalse();
    }


    public static synchronized UploadUtils newIncetance() {
        if (uploadUtils == null) {
            Configuration config = new Configuration.Builder()
                    .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                    .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                    .connectTimeout(10)           // 链接超时。默认10秒
                    .useHttps(true)               // 是否使用https上传域名
                    .responseTimeout(60)          // 服务器响应超时。默认60秒
                    .build();

            // 重用uploadManager。一般地，只需要创建一个uploadManager对象
            uploadManager = new UploadManager(config);
            uploadUtils = new UploadUtils();
            return uploadUtils;
        } else {
            return uploadUtils;
        }
    }

    @SuppressLint("CheckResult")
    public void uploadFile(final List<String> files, String token, final UploadListener uploadListener) {
        if (BuildConfig.DEBUG) Log.d("UploadUtils", token);
        this.uploadListener = uploadListener;
        final ArrayList<String> resultImagePath = new ArrayList<>();
        Observable
                // 依次发送list中的数据
                .fromIterable(files)
                // 变换，在这里上传图片
                // 修改为concatMap确保图片顺序
                .concatMap(new io.reactivex.functions.Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(String path) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Object>() {
                            @Override
                            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                                String key =UUID.randomUUID() + "." + path.split("\\.")[1];
                                if (BuildConfig.DEBUG) Log.d("UploadUtils", key);
                                ResponseInfo responseInfo = uploadManager.syncPut(path, key, token, null);
                                if (responseInfo.isOK()) {
                                    // 上传成功，发送这张图片的文件名
                                    emitter.onNext(key);
                                    emitter.onComplete();
                                } else {
                                    // 上传失败，告辞
                                    emitter.onError(new IOException(responseInfo.error));
                                }
                            }
                        }).subscribeOn(Schedulers.io());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        String s = o.toString();
                        if (BuildConfig.DEBUG) Log.d("UploadUtils", s);
                        resultImagePath.add(s);
                        if (resultImagePath.size() == files.size()) {
                            uploadListener.showUploadResult(resultImagePath);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("UploadUtils", "e:" + e);
                        uploadListener.showFalse();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}


