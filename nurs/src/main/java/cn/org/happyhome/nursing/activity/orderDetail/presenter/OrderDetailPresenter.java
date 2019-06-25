package cn.org.happyhome.nursing.activity.orderDetail.presenter;

import cn.org.happyhome.nursing.activity.orderDetail.contract.IOrderDetailContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.orderDetail.model.OrderDetailModel;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/29  16:01
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderDetailPresenter extends BasePresenterJv<IOrderDetailContract.View, IOrderDetailContract.Model> implements IOrderDetailContract.Presenter {

    @Override
    protected IOrderDetailContract.Model createModel() {
        return new OrderDetailModel();
    }

    @Override
    public void showOrderDetail(String orderId,String userId) {
        mModel.showOrderDetail(orderId, userId,mView);
    }
}

