package cn.org.happyhome.nursing.activity.setting.presenter;

import android.util.Log;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.activity.setting.contract.ISettingContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/12  9:54
 * - generate by MvpAutoCodePlus plugin.
 */

public class SettingPresenter extends BasePresenterJv<ISettingContract.View, ISettingContract.Model> implements ISettingContract.Presenter {

    @Override
    protected ISettingContract.Model createModel() {
        return new ISettingContract.Model() {
            @Override
            public void updateUserInfo(User user, String organizationID) {
                if (BuildConfig.DEBUG) Log.d("SettingPresenter", "++++");

                ApiManager.getInstence().getDailyService()
                        .updateUserInfo("a", user, organizationID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean<User>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
//                                d.dispose();
                            }

                            @Override
                            public void onNext(ResultBean<User> resultBean) {
                                mView.updateUserInfo(resultBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (BuildConfig.DEBUG) Log.d("SettingPresenter", "e:" + e);
                                mView.showErr("服务器繁忙，请稍后再试！");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

            @Override
            public void getUserInfo(String id, ISettingContract.View view) {
                ApiManager.getInstence().getDailyService()
                        .getUserInfo(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean<User>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResultBean<User> userResultBean) {
                                mView.getUserInfo(userResultBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (BuildConfig.DEBUG) Log.d("SettingPresenter", "e:" + e);
                                mView.showErr("");
                            }

                            @Override
                            public void onComplete() {

                            }
                        });

            }

            @Override
            public void addDisposable(Disposable disposable) {
                disposable.dispose();
            }

            @Override
            public void clearPool() {

            }
        };
    }

    @Override
    public void updateUserInfo(User user, String organizationID) {
        mModel.updateUserInfo(user, organizationID);
    }

    @Override
    public void getUserInfo(String id) {
        mModel.getUserInfo(id, mView);
    }
}

