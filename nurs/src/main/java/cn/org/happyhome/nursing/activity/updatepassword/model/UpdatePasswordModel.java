package cn.org.happyhome.nursing.activity.updatepassword.model;

import android.util.Log;

import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.BuildConfig;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;
import cn.org.happyhome.nursing.activity.updatepassword.contract.IUpdatePasswordContract;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/25  16:33
 * - generate by MvpAutoCodePlus plugin.
 */

public class UpdatePasswordModel extends DefaultDisposablePoolImpl implements IUpdatePasswordContract.Model {


    @Override
    public void updateUserInfo(final User user, String organizationID, final IUpdatePasswordContract.View view) {
        ApiManager.getInstence().getDailyService()
                .updateUserInfo("a", user, organizationID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        if (cn.org.happyhome.library.BuildConfig.DEBUG)
                            Log.d("UpdatePasswordModel", "resultBean:" + resultBean);
                        if (resultBean.getCode() == 0) {
                            view.updateUserInfoSuccess(resultBean);
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
    public void loginOut(User user, IUpdatePasswordContract.View view) {
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

