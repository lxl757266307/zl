package cn.org.happyhome.nursing.activity.chooseServiceContent.model;

import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.activity.chooseServiceContent.contract.IChooseServiceContentContract;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.AppealBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceContentBean;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/12/4  15:23
 * - generate by MvpAutoCodePlus plugin.
 */

public class ChooseServiceContentModel extends DefaultDisposablePoolImpl implements IChooseServiceContentContract.Model {

    @Override
    public void showServiceontent(String userId, IChooseServiceContentContract.View view) {
        ApiManager.getInstence().getDailyService()
                .findServiceContentByUserId(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<ServiceContentBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<List<ServiceContentBean>> resultBean) {
                        view.showServiceContent(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("ChooseServiceContentMod", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void saveServiceContent(List<ServiceContentBean> list, IChooseServiceContentContract.View view) {
        ApiManager.getInstence().getDailyService()
                .saveServiceContUserID(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<String> resultBean) {
                        view.saveServiceContent(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("ChooseServiceContentMod", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

