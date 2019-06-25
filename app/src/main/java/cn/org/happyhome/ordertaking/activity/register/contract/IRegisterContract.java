package cn.org.happyhome.ordertaking.activity.register.contract;

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
 * @date 2018/11/8  11:36
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IRegisterContract {
    interface View extends IView {

        void showRegister(ResultBean<User> resultBean);

        void showOrg(ResultBean<List<OrgBean>> resultBean);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {
        void register(User user, String organizationID);

        void showOrg();
    }

    interface Model extends IModel {
        void register(User user, String organizationID, View view);

        void showOrg(View view);
    }
}
