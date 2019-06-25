package cn.org.happyhome.nursing.activity.appeal.model;

import android.util.Log;

import java.util.ArrayList;

import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.BuildConfig;
import cn.org.happyhome.nursing.activity.appeal.contract.IAppealContract;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.AppealBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.utils.UploadUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AppealModel extends DefaultDisposablePoolImpl implements IAppealContract.Model, UploadUtils.UploadListener {

    String userId;
    String orderId;
    String description;
    IAppealContract.View view;

    @Override
    public void appealOrder(String userId, String orderId, String description, String token, ArrayList<String> files, IAppealContract.View view) {
        this.userId = userId;
        this.orderId = orderId;
        this.description = description;
        this.view = view;

        if (BuildConfig.DEBUG) Log.d("AppealModel", userId);
        if (BuildConfig.DEBUG) Log.d("AppealModel", orderId);
        if (BuildConfig.DEBUG) Log.d("AppealModel", description);
        UploadUtils.newIncetance().uploadFile(files, token, this);
    }


    @Override
    public void showUploadResult(ArrayList<String> keys) {

        ApiManager.getInstence().getDailyService()
                .appealOrder(new AppealBean(orderId, userId, description, keys))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (BuildConfig.DEBUG)
                            Log.d("AppealPresenter", "resultBean:" + resultBean);
                        view.showAppeal(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("AppealPresenter", "e:" + e);
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
