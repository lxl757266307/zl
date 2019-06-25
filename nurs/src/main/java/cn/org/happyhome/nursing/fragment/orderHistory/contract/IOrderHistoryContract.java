package cn.org.happyhome.nursing.fragment.orderHistory.contract;

import java.util.List;
import java.util.Map;

import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.VOrderMm;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/29  16:12
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IOrderHistoryContract {
    interface View extends IView {
        void showOrderList(ResultBean<List<VOrderMm>> resultBean);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {
        void showOrderList(Map<String, String> map);

    }

    interface Model extends IModel {
        void showOrderList(Map<String, String> map, View view);

    }
}
