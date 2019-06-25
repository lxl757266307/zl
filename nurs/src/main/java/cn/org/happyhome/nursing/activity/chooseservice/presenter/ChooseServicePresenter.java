package cn.org.happyhome.nursing.activity.chooseservice.presenter;

import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.activity.chooseservice.contract.IChooseServiceContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceTypeBean;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/12  14:08
 * - generate by MvpAutoCodePlus plugin.
 */

public class ChooseServicePresenter extends cn.org.happyhome.library.base.BasePresenterJv<IChooseServiceContract.View, IChooseServiceContract.Model> implements IChooseServiceContract.Presenter {

    @Override
    protected IChooseServiceContract.Model createModel() {
        return new IChooseServiceContract.Model() {
            @Override
            public void selectServiceType(String userType, String userId) {
                if (BuildConfig.DEBUG) Log.d("ChooseServicePresenter", "userType=" + userType);
                if (BuildConfig.DEBUG) Log.d("ChooseServicePresenter", "userId=" + userId);
                ApiManager.getInstence().getDailyService()
                        .findServiceTypeByUserID(userType, userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean<List<ServiceTypeBean>>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResultBean<List<ServiceTypeBean>> listResultBean) {
                                mView.showServiceList(listResultBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (BuildConfig.DEBUG) Log.d("ChooseServicePresenter", "e:" + e);
                                mView.showErr("服务器繁忙，请稍后再试！");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

            @Override
            public void addServiceType(String userType, String userId, List<ServiceTypeBean> list) {
                if (BuildConfig.DEBUG) Log.d("ChooseServicePresenter", userId);
                if (BuildConfig.DEBUG) Log.d("ChooseServicePresenter", userType);
                if (BuildConfig.DEBUG) Log.d("ChooseServicePresenter", "list:" + list);
                ApiManager.getInstence().getDailyService()
                        .addServiceTypeByUserID(userType, userId, list)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResultBean resultBean) {
                                mView.showAddCallBack(resultBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (BuildConfig.DEBUG) Log.d("ChooseServicePresenter", "e:" + e);
                                mView.showErr("服务器繁忙，请稍后再试！");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

            @Override
            public void addDisposable(Disposable disposable) {

            }

            @Override
            public void clearPool() {

            }
        };
    }

    @Override
    public void selectServiceType(String userType, String userId) {
        mModel.selectServiceType(userType, userId);
    }

    @Override
    public void addServiceType(String userType, String userId, List<ServiceTypeBean> list) {
        mModel.addServiceType(userType, userId, list);
    }
}

