package cn.org.happyhome.ordertaking.activity.register.model;

import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.ordertaking.activity.register.contract.IRegisterContract;
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
 * @date 2018/11/8  11:36
 * - generate by MvpAutoCodePlus plugin.
 */

public class RegisterModel extends DefaultDisposablePoolImpl implements IRegisterContract.Model {

    @Override
    public void register(User user, String organizationID, final IRegisterContract.View view) {
        ApiManager.getInstence().getDailyService()
                .register(user, organizationID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<User> resultBean) {
                        view.showRegister(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("RegisterModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void showOrg(final IRegisterContract.View view) {
        ApiManager.getInstence().getDailyService()
                .findAllOrg()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<OrgBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<List<OrgBean>> resultBean) {
                        view.showOrg(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RegisterModel", "e:" + e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

