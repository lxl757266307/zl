package cn.org.happyhome.nursing.fragment.center.presenter;

import android.util.Log;

import java.io.File;


import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.bean.User;
import cn.org.happyhome.nursing.fragment.center.contract.IPersonCenterContract;
import cn.org.happyhome.nursing.fragment.center.model.PersonCenterModel;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/27 0027  16:50
 * - generate by MvpAutoCodePlus plugin.
 */

public class PersonCenterPresenter extends BasePresenterJv<IPersonCenterContract.View, IPersonCenterContract.Model> implements IPersonCenterContract.Presenter {

    @Override
    protected IPersonCenterContract.Model createModel() {
        return new PersonCenterModel();
    }

    /**
     * @param token
     */
    @Override
    public void showUserInfo(String token) {
        mModel.getUserInfoFromIntenet(token, mView);
    }

    @Override
    public void showLoginOut(User user) {
        mModel.showLoginOut(user, mView);
    }

    @Override
    public void updateUserPhoto(String token, File file) {

        Log.d("PersonCenterPresenter", "mView:" + mView);
        mModel.updateUserPhoto(token, file, mView);
    }

}

