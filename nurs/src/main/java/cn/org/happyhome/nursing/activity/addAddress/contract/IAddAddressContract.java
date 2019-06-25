package cn.org.happyhome.nursing.activity.addAddress.contract;


import java.util.List;

import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.nursing.bean.AddAddressBean;
import cn.org.happyhome.nursing.bean.Address;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/29  18:04
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IAddAddressContract {
    interface View extends IView {
        void showAddressList(ResultBean<List<AddAddressBean>> resultBean);

        void bindAddress(ResultBean resultBean);

        void showErr(String msg);
    }

    interface Presenter extends IPresenter<View> {
        void getAddressList(String code,String userId);

        void bindAddress(String userId, String userType, List<AddAddressBean> list);
    }

    interface Model extends IModel {
        void getAddressList(String code, String userId, View view);

        void bindAddress(String userId, String userType, List<AddAddressBean> list, View view);
    }
}
