package cn.org.happyhome.nursing.activity.realName.contract;

import java.io.File;
import java.util.List;

import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/11/22  14:25
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IRealNameContract {
    interface View extends IView {

        void showRealName(ResultBean<String> resultBean);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {
        void showRealName(String userId, String carId, String token ,List<String> list);
    }

    interface Model extends IModel {
        void showRealName(String userId, String carId,String token , List<String> list, View view);

    }
}
