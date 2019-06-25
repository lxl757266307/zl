package cn.org.happyhome.nursing.fragment.center.contract;

import java.io.File;


import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/27 0027  16:50
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IPersonCenterContract {
    interface View extends IView {
        void showUserInfo(ResultBean<User> result);

        void showError(String err);

        void loginOut(ResultBean<User> resultBean);

        void updateUserPhoto(ResultBean<User> resultBean);
    }

    interface Presenter extends IPresenter<View> {
        void showUserInfo(String token);

        void showLoginOut(User user);

        void updateUserPhoto(String token, File file);
    }

    interface Model extends IModel {

        void getUserInfoFromIntenet(String token, View mView);

        void updateUserPhoto(String token, File file, View mView);

        void showLoginOut(User user, View view);
    }
}
