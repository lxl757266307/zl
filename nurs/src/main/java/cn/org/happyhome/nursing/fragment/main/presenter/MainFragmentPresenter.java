package cn.org.happyhome.nursing.fragment.main.presenter;


import cn.org.happyhome.library.base.BasePresenterJv;
import cn.org.happyhome.nursing.fragment.main.contract.IMainFragmentContract;
import cn.org.happyhome.nursing.fragment.main.model.MainFragmentModel;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/4 0004  9:35
 * - generate by MvpAutoCodePlus plugin.
 */

public class MainFragmentPresenter extends BasePresenterJv<IMainFragmentContract.View, IMainFragmentContract.Model> implements IMainFragmentContract.Presenter {

    @Override
    protected IMainFragmentContract.Model createModel() {
        return new MainFragmentModel();
    }

    @Override
    public void showLooperImage() {
        mModel.showLooperImage(mView);
    }

    @Override
    public void showNewsList() {
        mModel.showNewsList(mView);
    }
}

