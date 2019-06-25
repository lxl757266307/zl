package cn.org.happyhome.nursing.fragment.service.model;

import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.VOrderMm;
import cn.org.happyhome.nursing.fragment.service.contract.IOrderByStatusContract;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/25  14:29
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderByStatusModel extends DefaultDisposablePoolImpl implements IOrderByStatusContract.Model {

    @Override
    public void selectOrderByType(String type, int page, int pageSize, final IOrderByStatusContract.View view) {
        ApiManager.getInstence().getDailyService()
                .selectManyToMany(type, page, pageSize)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<VOrderMm>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<List<VOrderMm>> listResultBean) {
                        view.showOrderList(listResultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("OrderByStatusModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

