package cn.org.happyhome.nursing.activity.addAddress.model;


import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.activity.addAddress.contract.IAddAddressContract;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.AddAddressBean;
import cn.org.happyhome.nursing.bean.Address;
import cn.org.happyhome.nursing.bean.ResultBean;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/29  18:04
 * - generate by MvpAutoCodePlus plugin.
 */

public class AddAddressModel extends DefaultDisposablePoolImpl implements IAddAddressContract.Model {


    @Override
    public void getAddressList(String code, String userId, final IAddAddressContract.View view) {
        if (BuildConfig.DEBUG) Log.d("AddAddressModel", code);
        if (BuildConfig.DEBUG) Log.d("AddAddressModel", userId);
        ApiManager.getInstence().getDailyService()
                .selectAddressByCode(code, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<AddAddressBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBean<List<AddAddressBean>> resultBean) {
                        view.showAddressList(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("AddAddressModel", "e:" + e);
                        view.showErr(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void bindAddress(String userId, String userType, List<AddAddressBean> list, final IAddAddressContract.View view) {
        if (BuildConfig.DEBUG) Log.d("AddAddressModel", userId);
        if (BuildConfig.DEBUG) Log.d("AddAddressModel", userType);
        if (BuildConfig.DEBUG) Log.d("AddAddressModel", "list:" + list);
        ApiManager.getInstence().getDailyService()
                .bindAddress(userType, userId, list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        view.bindAddress(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("AddAddressModel", "e11111:" + e);
                        view.showErr("服务器繁忙，请稍后再试");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

