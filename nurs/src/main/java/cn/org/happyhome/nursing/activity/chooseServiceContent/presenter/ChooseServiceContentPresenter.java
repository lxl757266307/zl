package cn.org.happyhome.nursing.activity.chooseServiceContent.presenter;

import java.util.List;

import cn.org.happyhome.nursing.activity.chooseServiceContent.contract.IChooseServiceContentContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.chooseServiceContent.model.ChooseServiceContentModel;
import cn.org.happyhome.nursing.bean.ServiceContentBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/12/4  15:23
 * - generate by MvpAutoCodePlus plugin.
 */

public class ChooseServiceContentPresenter extends BasePresenterJv<IChooseServiceContentContract.View, IChooseServiceContentContract.Model> implements IChooseServiceContentContract.Presenter {

    @Override
    protected IChooseServiceContentContract.Model createModel() {
        return new ChooseServiceContentModel();
    }

    @Override
    public void showServiceontent(String userId) {
        mModel.showServiceontent(userId, mView);

    }

    @Override
    public void saveServiceContent(List<ServiceContentBean> list) {
        mModel.saveServiceContent(list, mView);
    }
}

