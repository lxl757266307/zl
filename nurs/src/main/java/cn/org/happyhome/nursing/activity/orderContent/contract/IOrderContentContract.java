package cn.org.happyhome.nursing.activity.orderContent.contract;

import java.util.List;

import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.nursing.bean.OrderContentBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceItemBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/26  11:02
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IOrderContentContract {
    interface View extends IView {

        void showOrderContent(ResultBean<List<OrderContentBean>> resultBean);

        void showTakeOrderResult(ResultBean<String> resultBean);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {
        void selectOrderContent(String orderId, String userId);

        void takeOrder(List<ServiceItemBean> list);
    }

    interface Model extends IModel {
        void selectOrderContent(String orderId, String userId, View view);

        void takeOrder(List<ServiceItemBean> list, View view);
    }
}
