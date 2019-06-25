package cn.org.happyhome.nursing.activity.appeal.presenter;

import android.app.Application;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.nursing.activity.appeal.contract.IAppealContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.appeal.model.AppealModel;
import cn.org.happyhome.nursing.api.ApiManager;
import cn.org.happyhome.nursing.bean.AppealBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.utils.UploadUtils;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/18  9:28
 * - generate by MvpAutoCodePlus plugin.
 */

public class AppealPresenter extends cn.org.happyhome.library.base.BasePresenterJv<IAppealContract.View, IAppealContract.Model>
        implements IAppealContract.Presenter {

    @Override
    protected IAppealContract.Model createModel() {
        return new AppealModel();
    }
//            @Override
//            public void appealOrder(String userId, String orderId, String description, ArrayList<String> files) {
//
//
//
//
//
//
//                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//                for (int i = 0; i < files.size(); i++) {
//                    builder.addFormDataPart("file", files.get(i).getName(),
//                            RequestBody.create(MediaType.parse("multipart/octet-stream"), files.get(i)));
//                }
//
//
//            @Override
//            public void addDisposable(Disposable disposable) {
//
//            }
//
//            @Override
//            public void clearPool() {
//
//            }
//
//            public RequestBody toRequestBody(String value) {
//                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), value);
//                return requestBody;
//            }


    @Override
    public void appealOrder(String userId, String orderId, String
            description, String token, ArrayList<String> files) {
        mModel.appealOrder(userId, orderId, description, token, files, mView);
    }
}

