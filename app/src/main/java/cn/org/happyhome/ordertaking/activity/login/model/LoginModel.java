package cn.org.happyhome.ordertaking.activity.login.model;

import android.util.Log;

import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.ordertaking.BuildConfig;
import cn.org.happyhome.ordertaking.activity.login.contract.ILoginContract;
import cn.org.happyhome.ordertaking.api.ApiManager;
import cn.org.happyhome.ordertaking.bean.ResultBean;
import cn.org.happyhome.ordertaking.bean.User;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/23 0023  15:49
 * - generate by MvpAutoCodePlus plugin.
 */

public class LoginModel extends DefaultDisposablePoolImpl implements ILoginContract.Model {


    @Override
    public void login(User map, final ILoginContract.View view) {


        if (BuildConfig.DEBUG) Log.d("LoginModel", "map:" + map);
        ApiManager.getInstence().getDailyService()
                .login(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBean<User> resultBean) {
                        if (BuildConfig.DEBUG) Log.d("LoginModel", resultBean.toString());
                        view.loginSuccess(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("LoginModel", "e:" + e);
                        view.loginFaild("服务器繁忙，请稍后再试！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}

