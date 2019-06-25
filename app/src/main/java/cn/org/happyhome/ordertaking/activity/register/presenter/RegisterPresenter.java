package cn.org.happyhome.ordertaking.activity.register.presenter;

import cn.org.happyhome.ordertaking.activity.register.contract.IRegisterContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.ordertaking.activity.register.model.RegisterModel;
import cn.org.happyhome.ordertaking.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/11/8  11:36
 * - generate by MvpAutoCodePlus plugin.
 */

public class RegisterPresenter extends BasePresenterJv<IRegisterContract.View, IRegisterContract.Model> implements IRegisterContract.Presenter {

    @Override
    protected IRegisterContract.Model createModel() {
        return new RegisterModel();
    }

    @Override
    public void register(User user,String organizationID) {
        mModel.register(user,organizationID, mView);
    }

    @Override
    public void showOrg() {
        mModel.showOrg(mView);
    }
}

