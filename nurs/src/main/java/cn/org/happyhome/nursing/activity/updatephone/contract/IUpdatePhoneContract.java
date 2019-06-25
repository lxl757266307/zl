package cn.org.happyhome.nursing.activity.updatephone.contract;


import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/25  15:19
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IUpdatePhoneContract {
    interface View extends IView {
        void updateUserInfoSuccess(ResultBean<User> userResultBean);

        void loginOut(ResultBean<User> userResultBean);


        void updateUserInfoFailed(String msg);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {
        void updateUserInfo(User user, String organizationID);

        void loginOut(User user);

    }

    interface Model extends IModel {
        void updateUserInfo(User user, String organizationID, IUpdatePhoneContract.View view);

        void loginOut(User user, View view);

    }
}
