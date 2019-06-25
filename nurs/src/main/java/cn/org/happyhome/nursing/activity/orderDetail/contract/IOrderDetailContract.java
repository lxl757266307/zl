package cn.org.happyhome.nursing.activity.orderDetail.contract;

import java.util.List;

import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.nursing.bean.OrderContentBean;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/29  16:01
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IOrderDetailContract {
    interface View extends IView {
        void showOrderDetail(ResultBean<List<OrderContentBean>> resultBean);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {
        void showOrderDetail(String orderId, String userId);
    }

    interface Model extends IModel {

        void showOrderDetail(String orderId, String userId, View view);
    }
}
