package cn.org.happyhome.nursing.activity.orderDetail.model;

import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.activity.orderDetail.contract.IOrderDetailContract;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.OrderContentBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/29  16:01
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderDetailModel extends DefaultDisposablePoolImpl implements IOrderDetailContract.Model {

    @Override
    public void showOrderDetail(String orderId, String userId, final IOrderDetailContract.View view) {
        ApiManager.getInstence().getDailyService()
                .selectOrderContent(orderId, userId,"2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<OrderContentBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<List<OrderContentBean>> resultBean) {
                        view.showOrderDetail(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("OrderDetailModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

