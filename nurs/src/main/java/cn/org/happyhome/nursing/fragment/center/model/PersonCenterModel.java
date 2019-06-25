package cn.org.happyhome.nursing.fragment.center.model;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.DefaultDisposablePoolImpl;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.api.RetrofitService;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;
import cn.org.happyhome.nursing.fragment.center.contract.IPersonCenterContract;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Description :
 *
 * @author long
 * @date 2018/8/27 0027  16:50
 * - generate by MvpAutoCodePlus plugin.
 */

public class PersonCenterModel extends DefaultDisposablePoolImpl implements IPersonCenterContract.Model {


    @Override
    public void getUserInfoFromIntenet(String token, final IPersonCenterContract.View view) {

        ApiManager.getInstence().getDailyService().getUserInfo(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<User> userResultBean) {
                        if (BuildConfig.DEBUG)
                            Log.d("PersonCenterModel", "userResultBean:" + userResultBean);
                        view.showUserInfo(userResultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("PersonCenterModel", "e:" + e);
                        view.showError("服务器繁忙，请稍后再试！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void updateUserPhoto(String token, File file, final IPersonCenterContract.View view) {

        if (BuildConfig.DEBUG) Log.d("PersonCenterModel", "file.length():" + file.length());
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

        builder.addFormDataPart("file", file.getName(),
                RequestBody.create(MediaType.parse("multipart/octet-stream"), file));
        // 0 代表修改用户 头像
        ApiManager.getInstence().getDailyService().uploadFile(builder.build(), token, "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<User> resultBean) {

                        if (BuildConfig.DEBUG)
                            Log.d("PersonCenterModel", "resultBean:" + resultBean);
                        view.updateUserPhoto(resultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("PersonCenterModel", "e:" + e);
                        view.showError("服务器繁忙，请稍后再试！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void showLoginOut(User user, IPersonCenterContract.View view) {
        if (BuildConfig.DEBUG) Log.d("PersonCenterModel", "user:" + user);
        ApiManager.getInstence().getDailyService()
                .loginOut(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultBean<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResultBean<User> userResultBean) {
                        view.loginOut(userResultBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (BuildConfig.DEBUG) Log.d("PersonCenterModel", "e:" + e);
                        view.showError("退出失败，请稍后重试！");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}

