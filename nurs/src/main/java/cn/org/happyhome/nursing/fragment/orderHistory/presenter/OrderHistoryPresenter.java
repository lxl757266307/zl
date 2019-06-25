package cn.org.happyhome.nursing.fragment.orderHistory.presenter;

import java.util.Map;

import cn.org.happyhome.nursing.fragment.orderHistory.contract.IOrderHistoryContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.fragment.orderHistory.model.OrderHistoryModel;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/29  16:12
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderHistoryPresenter extends BasePresenterJv<IOrderHistoryContract.View, IOrderHistoryContract.Model> implements IOrderHistoryContract.Presenter {

    @Override
    protected IOrderHistoryContract.Model createModel() {
        return new OrderHistoryModel();
    }

    @Override
    public void showOrderList(Map<String, String> map) {
        mModel.showOrderList(map, mView);
    }
}

