package cn.org.happyhome.nursing.activity.address.contract;

import java.util.List;


import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.nursing.bean.Address;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/29  15:24
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IAddressContract {
    interface View extends IView {
        void showAddressList(ResultBean<List<Address>> resultBean);

        void deleteAddress(ResultBean resultBean);

        void showErr(String msg);
    }

    interface Presenter extends IPresenter<View> {

        void getAddressList(String userId);

        void deleteAddressList( String userType,String userId, String addressId);
    }

    interface Model extends IModel {

        void getAddressList(String userId, View view);

        void deleteAddressList( String userType,String userId, String addressId, View view);
    }
}
