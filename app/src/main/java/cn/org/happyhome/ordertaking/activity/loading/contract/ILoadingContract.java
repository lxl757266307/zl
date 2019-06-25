package cn.org.happyhome.ordertaking.activity.loading.contract;


import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IView;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/22 0022  11:03
 * - generate by MvpAutoCodePlus plugin.
 */

public interface ILoadingContract {
    interface View extends IView {
    }

    interface Presenter extends IPresenter<View> {
    }

    interface Model extends IModel {
    }
}
