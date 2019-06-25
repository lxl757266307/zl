package cn.org.happyhome.ordertaking.activity.forgotPassword.presenter;

import cn.org.happyhome.ordertaking.activity.forgotPassword.contract.IForgotPasswordContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.ordertaking.activity.forgotPassword.model.ForgotPasswordModel;
import cn.org.happyhome.ordertaking.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/12/12  14:48
 * - generate by MvpAutoCodePlus plugin.
 */

public class ForgotPasswordPresenter extends BasePresenterJv<IForgotPasswordContract.View, IForgotPasswordContract.Model> implements IForgotPasswordContract.Presenter {

    @Override
    protected IForgotPasswordContract.Model createModel() {
        return new ForgotPasswordModel();
    }

    @Override
    public void updatePassword(User user) {
        mModel.updatePassword(user, mView);
    }

}

