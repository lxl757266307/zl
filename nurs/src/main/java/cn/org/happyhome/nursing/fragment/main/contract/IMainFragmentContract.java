package cn.org.happyhome.nursing.fragment.main.contract;


import java.util.List;

import cn.org.happyhome.library.base.IModel;
import cn.org.happyhome.library.base.IPresenter;
import cn.org.happyhome.library.base.IView;
import cn.org.happyhome.nursing.bean.NewsInfomationBean;
import cn.org.happyhome.nursing.bean.ResultBean;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/4 0004  9:35
 * - generate by MvpAutoCodePlus plugin.
 */

public interface IMainFragmentContract {
    interface View extends IView {

        void showLooperImage(ResultBean<List<NewsInfomationBean>> listResultBean);

        void showNewsList(ResultBean<List<NewsInfomationBean>> newsList);

        void showErr();
    }

    interface Presenter extends IPresenter<View> {
        void showLooperImage();

        void showNewsList();
    }

    interface Model extends IModel {

        void showLooperImage(View view);

        void showNewsList(View view);
    }
}
