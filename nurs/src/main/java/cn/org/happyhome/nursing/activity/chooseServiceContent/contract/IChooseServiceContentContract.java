package cn.org.happyhome.nursing.activity.chooseServiceContent.contract;

import java.util.List;

import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceContentBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/12/4  15:23
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IChooseServiceContentContract {
    interface View extends IView {

        void showServiceContent(ResultBean<List<ServiceContentBean>> resultBean);

        void saveServiceContent(ResultBean<String> resultBean);

        void showErr();

    }

    interface Presenter extends IPresenter<View> {
        void showServiceontent(String userId);

        void saveServiceContent(List<ServiceContentBean> list);
    }

    interface Model extends IModel {
        void showServiceontent(String userId, View view);

        void saveServiceContent(List<ServiceContentBean> list, View view);
    }
}
