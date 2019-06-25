package cn.org.happyhome.nursing.activity.setting.contract;

import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/12  9:53
 * - generate by MvpAutoCodePlus plugin.
 */

public interface ISettingContract {
    interface View extends IView {

        void updateUserInfo(ResultBean<User> resultBean);

        void getUserInfo(ResultBean<User> resultBean);

        void showErr(String msg);
    }

    interface Presenter extends IPresenter<View> {

        void updateUserInfo(User user,  String organizationID);

        void getUserInfo(String id);
    }

    interface Model extends IModel {
        void updateUserInfo(User user,  String organizationID);

        void getUserInfo(String id, View view);
    }
}
