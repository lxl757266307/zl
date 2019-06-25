package cn.org.happyhome.nursing.activity.updatephone.presenter;

import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.updatephone.contract.IUpdatePhoneContract;
import cn.org.happyhome.nursing.activity.updatephone.model.UpdatePhoneModel;
import cn.org.happyhome.nursing.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/25  15:19
 * - generate by MvpAutoCodePlus plugin.
 */

public class UpdatePhonePresenter extends BasePresenterJv<IUpdatePhoneContract.View, IUpdatePhoneContract.Model> implements IUpdatePhoneContract.Presenter {

    @Override
    protected IUpdatePhoneContract.Model createModel() {
        return new UpdatePhoneModel();
    }

    @Override
    public void updateUserInfo(User user,  String organizationID) {
        mModel.updateUserInfo(user,organizationID, mView);
    }

    @Override
    public void loginOut(User user) {
        mModel.loginOut(user,mView);
    }
}

