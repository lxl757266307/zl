package cn.org.happyhome.ordertaking.activity.login.contract;

import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.ordertaking.bean.ResultBean;
import cn.org.happyhome.ordertaking.bean.User;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/23 0023  15:49
 * - generate by MvpAutoCodePlus plugin.
 */

public interface ILoginContract {
    interface View extends IView {

        void loginSuccess(ResultBean<User> resultBean);

        void loginFaild(String msg);

    }

    interface Presenter extends IPresenter<View> {

        void login(User map);
    }

    interface Model extends IModel {

        void login(User map, View view);

    }
}
