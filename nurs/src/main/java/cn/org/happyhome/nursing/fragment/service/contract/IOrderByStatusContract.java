package cn.org.happyhome.nursing.fragment.service.contract;

import java.util.List;

import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.VOrderMm;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/25  14:29
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IOrderByStatusContract {
    interface View extends IView {

        void showOrderList(ResultBean<List<VOrderMm>> resultBean);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {
        void selectOrderByType(String type, int page, int pageSize);
    }

    interface Model extends IModel {
        void selectOrderByType(String type, int page, int pageSize, View view);
    }
}
