package cn.org.happyhome.nursing.activity.appeal.contract;

import java.io.File;
import java.util.ArrayList;

import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/18  9:28
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IAppealContract {
    interface View extends IView {
        void showAppeal(ResultBean resultBean);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {

        void appealOrder(String userId, String orderId, String description, String token, ArrayList<String> files);
    }

    public interface Model extends IModel {

        void appealOrder(String userId, String orderId, String description, String token, ArrayList<String> files, View view);
    }
}
