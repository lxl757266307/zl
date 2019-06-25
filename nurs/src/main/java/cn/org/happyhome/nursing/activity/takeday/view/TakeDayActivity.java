package cn.org.happyhome.nursing.activity.takeday.view;

import android.widget.CalendarView;

import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.takeday.contract.ITakeDayContract;
import cn.org.happyhome.nursing.activity.takeday.presenter.TakeDayPresenter;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/14 0014  12:06
 * - generate by MvpAutoCodePlus plugin.
 */

public class TakeDayActivity extends BaseMvpActivity<ITakeDayContract.View, ITakeDayContract.Presenter> implements ITakeDayContract.View {


    CalendarView calendarView;

    @Override
    public void initListener() {
        setContentView(R.layout.activity_take_day);
        this.calendarView = (CalendarView) findViewById(R.id.calendarView);
    }

    @Override
    public ITakeDayContract.Presenter createPresenter() {
        return new TakeDayPresenter();
    }

    @Override
    public ITakeDayContract.View createView() {
        return this;
    }


}

