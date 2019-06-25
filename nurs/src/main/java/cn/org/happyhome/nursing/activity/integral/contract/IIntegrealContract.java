package cn.org.happyhome.nursing.activity.integral.contract;

import java.util.List;

import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.TuserSumdetails;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/16  10:48
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IIntegrealContract {
    interface View extends IView {

        void showIntegral(ResultBean<List<TuserSumdetails>> resultBean);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {

        void showIntegral(int page, int pageSize, String userId);
    }

    public interface Model extends IModel {
        void showIntegral(int page, int pageSize, String userId, View view);
    }
}
