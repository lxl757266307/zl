package cn.org.happyhome.ordertaking.activity.login.presenter;

import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.ordertaking.activity.login.contract.ILoginContract;
import cn.org.happyhome.ordertaking.activity.login.model.LoginModel;
import cn.org.happyhome.ordertaking.bean.User;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/23 0023  15:49
 * - generate by MvpAutoCodePlus plugin.
 */

public class LoginPresenter extends BasePresenterJv<ILoginContract.View, ILoginContract.Model> implements ILoginContract.Presenter {

    @Override
    protected ILoginContract.Model createModel() {
        return new LoginModel();
    }


    @Override
    public void login(User map) {
        mModel.login(map, mView);
    }
}

