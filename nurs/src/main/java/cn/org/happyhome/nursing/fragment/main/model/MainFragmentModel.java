package cn.org.happyhome.nursing.fragment.main.model;


import android.util.Log;

import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.NewsInfomationBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.fragment.main.contract.IMainFragmentContract;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/4 0004  9:35
 * - generate by MvpAutoCodePlus plugin.
 */

public class MainFragmentModel extends DefaultDisposablePoolImpl implements IMainFragmentContract.Model {

    @Override
    public void showLooperImage(final IMainFragmentContract.View view) {
        ApiManager.getInstence().getDailyService()
                .downloadAll("1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<NewsInfomationBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<List<NewsInfomationBean>> listResultBean) {
                        view.showLooperImage(listResultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("MainFragmentModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void showNewsList(final IMainFragmentContract.View view) {
        ApiManager.getInstence().getDailyService()
                .downloadAll("2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<List<NewsInfomationBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<List<NewsInfomationBean>> listResultBean) {
                        view.showNewsList(listResultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("MainFragmentModel", "e:" + e);
                        view.showErr();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

