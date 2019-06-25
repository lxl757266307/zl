package cn.org.happyhome.ordertaking.activity.forgotPassword.contract;

import java.util.List;

import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.ordertaking.bean.OrgBean;
import cn.org.happyhome.ordertaking.bean.ResultBean;
import cn.org.happyhome.ordertaking.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/12/12  14:48
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IForgotPasswordContract {
    interface View extends IView {
        void showErr();
        void updatePassword(ResultBean<String> resultBean);
    }

    interface Presenter extends IPresenter<View> {
        void updatePassword(User user);

    }

    interface Model extends IModel {
        void updatePassword(User user, View view);

    }
}
