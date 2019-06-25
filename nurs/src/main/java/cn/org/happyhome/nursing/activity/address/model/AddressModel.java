package cn.org.happyhome.nursing.activity.address.model;

import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.activity.address.contract.IAddressContract;
import cn.org.happyhome.nursing.api.ApiManager;
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
 * @date 2018/9/29  15:24
 * - generate by MvpAutoCodePlus plugin.
 */

public class AddressModel extends DefaultDisposablePoolImpl implements IAddressContract.Model {

    @Override
    public void getAddressList(String userId, final IAddressContract.View view) {
        ApiManager.getInstence().getDailyService()
                .getAddressList(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<Address>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<List<Address>> listResultBean) {
                        view.showAddressList(listResultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("AddressModel", "e:" + e);
                        view.showErr("服务器繁忙，请稍后再试！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void deleteAddressList(String userType, String userId, String addressId, final IAddressContract.View view) {
        ApiManager.getInstence().getDailyService()
                .deleteAddress(userType, userId, addressId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean resultBean) {
                        view.deleteAddress(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.showErr("服务器繁忙，请稍后再试！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

