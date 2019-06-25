package cn.org.happyhome.nursing.activity.orderContent.presenter;

import java.util.List;

import cn.org.happyhome.nursing.activity.orderContent.contract.IOrderContentContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.orderContent.model.OrderContentModel;
import cn.org.happyhome.nursing.bean.ServiceItemBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/26  11:02
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderContentPresenter extends BasePresenterJv<IOrderContentContract.View, IOrderContentContract.Model> implements IOrderContentContract.Presenter {

    @Override
    protected IOrderContentContract.Model createModel() {
        return new OrderContentModel();
    }

    @Override
    public void selectOrderContent(String orderId, String userId) {
        mModel.selectOrderContent(orderId, userId, mView);
    }

    @Override
    public void takeOrder(List<ServiceItemBean> list) {
        mModel.takeOrder(list, mView);
    }
}

