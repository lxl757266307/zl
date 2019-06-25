package cn.org.happyhome.nursing.activity.integral.presenter;

import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.activity.integral.contract.IIntegrealContract;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.TuserSumdetails;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/16  10:48
 * - generate by MvpAutoCodePlus plugin.
 */

public class IntegrealPresenter extends cn.org.happyhome.library.base.BasePresenterJv<IIntegrealContract.View, IIntegrealContract.Model> implements IIntegrealContract.Presenter {

    @Override
    protected IIntegrealContract.Model createModel() {
        return new IIntegrealContract.Model() {
            @Override
            public void showIntegral(int page, int pageSize, String userId, IIntegrealContract.View view) {
                if (BuildConfig.DEBUG) Log.d("IntegrealPresenter", "userId:"+userId);
                if (BuildConfig.DEBUG) Log.d("IntegrealPresenter", "page:" + page);
                if (BuildConfig.DEBUG) Log.d("IntegrealPresenter", "pageSize:" + pageSize);
                ApiManager.getInstence().getDailyService()
                        .selectSumdetailsByUserid(userId, page, pageSize)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean<List<TuserSumdetails>>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(ResultBean<List<TuserSumdetails>> resultBean) {
                                mView.showIntegral(resultBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (BuildConfig.DEBUG) Log.d("IntegrealPresenter", "e:" + e);
                                mView.showErr();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

            @Override
            public void addDisposable(Disposable disposable) {

            }

            @Override
            public void clearPool() {

            }
        };
    }

    @Override
    public void showIntegral(int page, int pageSize, String userId) {
        mModel.showIntegral(page, pageSize, userId, mView);
    }
}

