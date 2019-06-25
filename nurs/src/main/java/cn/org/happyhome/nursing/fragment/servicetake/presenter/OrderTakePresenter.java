package cn.org.happyhome.nursing.fragment.servicetake.presenter;


import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.fragment.servicetake.contract.IOrderTakeContract;
import cn.org.happyhome.nursing.fragment.servicetake.model.OrderTakeModel;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/27 0027  11:58
 * - generate by MvpAutoCodePlus plugin.
 */

public class OrderTakePresenter extends BasePresenterJv<IOrderTakeContract.View, IOrderTakeContract.Model> implements IOrderTakeContract.Presenter {

    @Override
    protected IOrderTakeContract.Model createModel() {
        return new OrderTakeModel();
    }
}

