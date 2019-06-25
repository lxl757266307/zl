package cn.org.happyhome.nursing.fragment.service.presenter;

import cn.org.happyhome.nursing.fragment.service.contract.IOrderByStatusContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.fragment.service.model.OrderByStatusModel;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/25  14:29
 * - generate by MvpAutoCodePlus plugin.
 */

public class ManyToMantPresenter extends BasePresenterJv<IOrderByStatusContract.View, IOrderByStatusContract.Model> implements IOrderByStatusContract.Presenter {

    @Override
    protected IOrderByStatusContract.Model createModel() {
        return new OrderByStatusModel();
    }

    @Override
    public void selectOrderByType(String type, int page, int pageSize) {
        mModel.selectOrderByType(type, page, pageSize, mView);
    }
}

