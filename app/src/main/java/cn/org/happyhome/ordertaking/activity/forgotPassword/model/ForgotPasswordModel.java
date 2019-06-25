package cn.org.happyhome.ordertaking.activity.forgotPassword.model;

import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.ordertaking.activity.forgotPassword.contract.IForgotPasswordContract;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.ordertaking.api.ApiManager;
import cn.org.happyhome.ordertaking.bean.OrgBean;
import cn.org.happyhome.ordertaking.bean.ResultBean;
import cn.org.happyhome.ordertaking.bean.User;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/12/12  14:48
 * - generate by MvpAutoCodePlus plugin.
 */

public class ForgotPasswordModel extends DefaultDisposablePoolImpl implements IForgotPasswordContract.Model {

    @Override
    public void updatePassword(User user, final IForgotPasswordContract.View view) {

        ApiManager.getInstence().getDailyService()
                .updatePassword(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        view.updatePassword(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("ForgotPasswordModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}

