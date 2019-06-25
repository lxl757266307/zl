package cn.org.happyhome.nursing.activity.realName.presenter;

import java.io.File;
import java.util.List;

import cn.org.happyhome.nursing.activity.realName.contract.IRealNameContract;
import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.activity.realName.model.RealNameModel;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/11/22  14:25
 * - generate by MvpAutoCodePlus plugin.
 */

public class RealNamePresenter extends BasePresenterJv<IRealNameContract.View, IRealNameContract.Model> implements IRealNameContract.Presenter {

    @Override
    protected IRealNameContract.Model createModel() {
        return new RealNameModel();
    }

    @Override
    public void showRealName(String userId, String cardId, String token, List<String> list) {
        mModel.showRealName(userId, cardId, token, list, mView);
    }
}

