package cn.org.happyhome.nursing.activity.main.presenter;

import android.util.Log;
import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.main.contract.IMainContract;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.ResultBean;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/6 0006  9:57
 * - generate by MvpAutoCodePlus plugin.
 */

public class MainPresenter extends BasePresenterJv<IMainContract.View, IMainContract.Model> implements IMainContract.Presenter {

    @Override
    protected IMainContract.Model createModel() {
        return new IMainContract.Model() {
            @Override
            public void showJupushRegister(String registerId, String userId) {

                ApiManager.getInstence().getDailyService()
                        .registerJPUSH(userId, registerId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResultBean resultBean) {
                                mView.showJupushRegister(resultBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (BuildConfig.DEBUG) Log.d("MainPresenter", "e:" + e);
                                mView.showErr();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

            @Override
            public void showRealName(String userId, IMainContract.View view) {
                ApiManager.getInstence().getDailyService()
                        .getAuthFlag(userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResultBean resultBean) {
                                mView.showRealName(resultBean);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (BuildConfig.DEBUG) Log.d("MainPresenter", "e:" + e);
                                mView.showErr();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }

            @Override
            public void showToken(IMainContract.View view) {
                ApiManager.getInstence().getDailyService()
                        .getToken()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<ResultBean<String>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(ResultBean<String> resultBean) {
                                mView.showQNToken(resultBean);
                            }

                            @Override
                            public void onError(Throwable e) {
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
    public void showJupushRegister(String registerId, String userId) {
        mModel.showJupushRegister(registerId, userId);
    }

    @Override
    public void showRealName(String userId) {
        mModel.showRealName(userId, mView);
    }

    @Override
    public void showToken() {
        mModel.showToken(mView);
    }
}

