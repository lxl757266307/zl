package cn.org.happyhome.nursing.activity.chooseservice.contract;

import java.util.List;

import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceTypeBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/12  14:08
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IChooseServiceContract {
    interface View extends IView {

        void showServiceList(ResultBean<List<ServiceTypeBean>> resultBean);

        void showAddCallBack(ResultBean resultBean);

        void showErr(String err);
    }

    interface Presenter extends IPresenter<View> {

        void selectServiceType(String userType, String userId);

        void addServiceType(String userType, String userId, List<ServiceTypeBean> list);

    }

    interface Model extends IModel {
        void selectServiceType(String userType, String userId);

        void addServiceType(String userType, String userId, List<ServiceTypeBean> list);

    }
}
