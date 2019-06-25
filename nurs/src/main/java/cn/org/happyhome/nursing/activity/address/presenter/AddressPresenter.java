package cn.org.happyhome.nursing.activity.address.presenter;

import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.address.contract.IAddressContract;
import cn.org.happyhome.nursing.activity.address.model.AddressModel;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/29  15:24
 * - generate by MvpAutoCodePlus plugin.
 */

public class AddressPresenter extends BasePresenterJv<IAddressContract.View, IAddressContract.Model> implements IAddressContract.Presenter {

    @Override
    protected IAddressContract.Model createModel() {
        return new AddressModel();
    }

    @Override
    public void getAddressList(String userId) {
        mModel.getAddressList(userId, mView);
    }

    @Override
    public void deleteAddressList( String userType,String userId, String addressId) {
        mModel.deleteAddressList(userType,userId, addressId, mView);
    }
}

