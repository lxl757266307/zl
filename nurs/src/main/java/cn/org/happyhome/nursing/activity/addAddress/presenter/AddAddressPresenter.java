package cn.org.happyhome.nursing.activity.addAddress.presenter;

import java.util.List;

import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.addAddress.contract.IAddAddressContract;
import cn.org.happyhome.nursing.activity.addAddress.model.AddAddressModel;
import cn.org.happyhome.nursing.bean.AddAddressBean;
import cn.org.happyhome.nursing.bean.Address;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/29  18:04
 * - generate by MvpAutoCodePlus plugin.
 */

public class AddAddressPresenter extends BasePresenterJv<IAddAddressContract.View, IAddAddressContract.Model> implements IAddAddressContract.Presenter {

    @Override
    protected IAddAddressContract.Model createModel() {
        return new AddAddressModel();
    }

    @Override
    public void getAddressList(String code, String userId) {
        mModel.getAddressList(code, userId, mView);
    }

    @Override
    public void bindAddress(String userId, String userType, List<AddAddressBean> list) {
        mModel.bindAddress(userId, userType, list, mView);
    }
}

