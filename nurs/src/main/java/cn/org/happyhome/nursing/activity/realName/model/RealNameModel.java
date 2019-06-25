package cn.org.happyhome.nursing.activity.realName.model;

import android.util.Log;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.activity.realName.contract.IRealNameContract;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.utils.UploadUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/11/22  14:25
 * - generate by MvpAutoCodePlus plugin.
 */

public class RealNameModel extends DefaultDisposablePoolImpl implements IRealNameContract.Model, UploadUtils.UploadListener {

    String userId;
    String cardId;
    IRealNameContract.View view;

    public RequestBody toRequestBody(String value) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
        return requestBody;
    }

    @Override
    public void showRealName(String userId, String cardId, String token, List<String> list, IRealNameContract.View view) {
        this.userId = userId;
        this.cardId = cardId;
        this.view = view;

        UploadUtils.newIncetance().uploadFile(list, token, this);


    }

    @Override
    public void showUploadResult(ArrayList<String> key) {
        ApiManager.getInstence().getDailyService()
                .realName(userId, cardId, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        view.showRealName(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("RealNameModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void showFalse() {
        view.showErr();
    }
}

