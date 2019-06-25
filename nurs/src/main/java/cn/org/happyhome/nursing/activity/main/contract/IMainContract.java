package cn.org.happyhome.nursing.activity.main.contract;


import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/6 0006  9:57
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IMainContract {
    interface View extends IView {

        void showJupushRegister(ResultBean resultBean);

        void showQNToken(ResultBean<String> resultBean);

        void showRealName(ResultBean<String> resultBean);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {
        void showJupushRegister(String registerId, String userId);

        void showRealName(String userId);

        void showToken();
    }

    interface Model extends IModel {
        void showJupushRegister(String registerId, String userId);

        void showRealName(String userId,View view);

        void showToken(View view);
    }
}
