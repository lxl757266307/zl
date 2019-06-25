package cn.org.happyhome.nursing.activity.orderContent.model;

import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.activity.orderContent.contract.IOrderContentContract;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.OrderContentBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceItemBean;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/26  11:02
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderContentModel extends DefaultDisposablePoolImpl implements IOrderContentContract.Model {

    @Override
    public void selectOrderContent(String orderId,String userId, final IOrderContentContract.View view) {
        ApiManager.getInstence().getDailyService()
                .selectOrderContent(orderId,userId,"1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<OrderContentBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<List<OrderContentBean>> resultBean) {
                        view.showOrderContent(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("OrderContentModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void takeOrder(List<ServiceItemBean> list, final IOrderContentContract.View view) {

        if (BuildConfig.DEBUG) Log.d("OrderContentModel", "list:" + list);
        ApiManager.getInstence().getDailyService()
                .takeOrderById(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<String> resultBean) {
                        view.showTakeOrderResult(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("OrderContentModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

