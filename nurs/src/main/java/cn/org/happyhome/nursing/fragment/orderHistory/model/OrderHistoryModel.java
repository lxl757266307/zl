package cn.org.happyhome.nursing.fragment.orderHistory.model;

import android.util.Log;

import java.util.List;
import java.util.Map;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.VOrderMm;
import cn.org.happyhome.nursing.fragment.orderHistory.contract.IOrderHistoryContract;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/29  16:12
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderHistoryModel extends DefaultDisposablePoolImpl implements IOrderHistoryContract.Model {

    @Override
    public void showOrderList(Map<String, String> map, final IOrderHistoryContract.View view) {
        ApiManager.getInstence().getDailyService()
                .selectUserOrderByStatus(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<VOrderMm>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<List<VOrderMm>> resultBean) {
                        view.showOrderList(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("OrderHistoryModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

