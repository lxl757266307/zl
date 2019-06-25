package cn.org.happyhome.nursing.activity.updatepassword.presenter;

import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.updatepassword.contract.IUpdatePasswordContract;
import cn.org.happyhome.nursing.activity.updatepassword.model.UpdatePasswordModel;
import cn.org.happyhome.nursing.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/25  16:33
 * - generate by MvpAutoCodePlus plugin.
 */

public class UpdatePasswordPresenter extends BasePresenterJv<IUpdatePasswordContract.View, IUpdatePasswordContract.Model> implements IUpdatePasswordContract.Presenter {

    @Override
    protected IUpdatePasswordContract.Model createModel() {
        return new UpdatePasswordModel();
    }

    @Override
    public void updateUserInfo(User user, String organizationID) {
        mModel.updateUserInfo(user, organizationID, mView);
    }

    @Override
    public void loginOut(User user) {
        mModel.loginOut(user,mView);
    }
}

