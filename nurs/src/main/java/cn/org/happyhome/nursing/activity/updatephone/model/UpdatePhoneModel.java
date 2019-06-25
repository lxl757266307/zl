package cn.org.happyhome.nursing.activity.updatephone.model;


import android.util.Log;

import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.BuildConfig;
import cn.org.happyhome.nursing.activity.updatephone.contract.IUpdatePhoneContract;
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
 * @date 2018/9/25  15:19
 * - generate by MvpAutoCodePlus plugin.
 */

public class UpdatePhoneModel extends DefaultDisposablePoolImpl implements IUpdatePhoneContract.Model {


    @Override
    public void updateUserInfo(User user,   String organizationID,final IUpdatePhoneContract.View view) {

        if (cn.org.happyhome.library.BuildConfig.DEBUG) Log.d("UpdatePhoneModel", "user:" + user);
        ApiManager.getInstence().getDailyService()
                .updateUserInfo("a", user,organizationID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {

                        if (resultBean.getCode() == 0) {
                            view.updateUserInfoSuccess(resultBean);
//                            view.reload();
                        } else if (resultBean.getCode() == 201) {
                            view.updateUserInfoFailed("当前手机号已存在");
                        } else if (resultBean.getCode() == 1) {
                            view.updateUserInfoFailed("更新失败");
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("UpdatePhoneModel", "e:" + e);
                        view.updateUserInfoFailed("服务器繁忙，请稍后再试！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loginOut(User user, IUpdatePhoneContract.View view) {
        ApiManager.getInstence().getDailyService()
                .loginOut(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<User> userResultBean) {
                        view.loginOut(userResultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (cn.org.happyhome.library.BuildConfig.DEBUG) Log.d("PersonCenterModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

