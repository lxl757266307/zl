package cn.org.happyhome.nursing.activity.main.view;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.main.contract.IMainContract;
import cn.org.happyhome.nursing.activity.main.presenter.MainPresenter;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.fragment.center.view.PersonCenterFragment;
import cn.org.happyhome.nursing.fragment.main.view.MainFragment;
import cn.org.happyhome.nursing.fragment.servicetake.view.OrderTakeFragment;
import cn.org.happyhome.nursing.service.MyJobService;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/6 0006  9:57
 * - generate by MvpAutoCodePlus plugin.
 */

@RuntimePermissions
public class MainActivity extends BaseMvpActivity<IMainContract.View, IMainContract.Presenter> implements IMainContract.View, View.OnClickListener {

    ViewPager viewPager;
    SharedPreferences sharedPreferences;
    TextView tvHome;
    TextView tvService;
    TextView tvCenter;
    LinearLayout layoutBottom;
    ConstraintLayout container;
    String userId;
    String registerId;

    UpdateRealNameStateBroadReciver updateRealNameStateBroadReciver;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);
        MainActivityPermissionsDispatcher.callLocationWithPermissionCheck(this);
        setContentView(R.layout.activity_main);
        String s = sHA1(this);
        Log.d("MainActivity", "s=====" + s);
        layoutBottom = (LinearLayout) findViewById(R.id.layout_bottom);
        tvCenter = (TextView) findViewById(R.id.tv_center);
        tvService = (TextView) findViewById(R.id.tv_service);
        tvHome = (TextView) findViewById(R.id.tv_home);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tvCenter.setOnClickListener(this);
        tvService.setOnClickListener(this);
        tvHome.setOnClickListener(this);
        List<Fragment> list = new ArrayList<>();
        list.add(new MainFragment());
        list.add(new OrderTakeFragment());
        list.add(new PersonCenterFragment());
        FragmentAdater fragmentAdater = new FragmentAdater(getSupportFragmentManager(), list);
        viewPager.setAdapter(fragmentAdater);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(onPageChangeListener);

        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        boolean flag = sharedPreferences.getBoolean(Const.HAD_REGISTER, false);

        if (BuildConfig.DEBUG) Log.d("MainActivity", "userId==" + userId);
        if (BuildConfig.DEBUG) Log.d("MainActivity", "flag:" + flag);
        mPresenter.showJupushRegister(userId, userId);
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, userId));
        mPresenter.showToken();
        if (userId != null) {
            mPresenter.showRealName(userId);
        }

        initReciver();
//        initService();

    }

    private void initReciver() {
        updateRealNameStateBroadReciver = new UpdateRealNameStateBroadReciver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.UPDATE_REAL_NAME_STATE);
        this.registerReceiver(updateRealNameStateBroadReciver, intentFilter);
    }


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onPageSelected(int i) {
            resetTabState();
            switch (i) {
                case 0:
                    setTabState(tvHome, R.mipmap.home_blue1, getColor(R.color.customer_color));
                    break;
                case 1:
                    setTabState(tvService, R.mipmap.service_blue1, getColor(R.color.customer_color));
                    break;
                case 2:
                    setTabState(tvCenter, R.mipmap.center_blue1, getColor(R.color.customer_color));
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };


    @Override
    public IMainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public IMainContract.View createView() {
        return this;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        resetTabState();
        if (view.getId() == R.id.tv_home) {
            viewPager.setCurrentItem(0);
            setTabState(tvHome, R.mipmap.home_blue1, getColor(R.color.customer_color));
        } else if (view.getId() == R.id.tv_service) {
            viewPager.setCurrentItem(1);
            setTabState(tvService, R.mipmap.service_blue1, getColor(R.color.customer_color));
        } else if (view.getId() == R.id.tv_center) {
            viewPager.setCurrentItem(2);
            setTabState(tvCenter, R.mipmap.center_blue1, getColor(R.color.customer_color));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void resetTabState() {
        setTabState(tvHome, R.mipmap.home_black1, getColor(R.color.black));
        setTabState(tvService, R.mipmap.service_black1, getColor(R.color.black));
        setTabState(tvCenter, R.mipmap.center_black1, getColor(R.color.black));

    }


    private void setTabState(TextView textView, int image, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, image, 0, 0);//Call requires API level 17
        }
        textView.setTextColor(color);
    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void callLocation() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void showJupushRegister(ResultBean resultBean) {
        if (BuildConfig.DEBUG) Log.d("MainActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
//            ToolUitls.toast(this, "推送已注册！");
        }
    }

    @Override
    public void showQNToken(ResultBean<String> resultBean) {
        if (BuildConfig.DEBUG) Log.d("MainActivity", "resultBean:1111111111==" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            String data = resultBean.getData();
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Const.QN_TOKEN, data);
            edit.apply();
        }
    }

    @Override
    public void showRealName(ResultBean<String> resultBean) {
        if (BuildConfig.DEBUG) Log.d("MainActivity", "resultBean11111:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            String data = resultBean.getData();
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Const.REAL_NAME_STATE, data);
            edit.apply();
            if (Const.REAL_NAME_STATE_9981.equals(data)) {
                ToolUitls.toast(this, "实名认证失败，请尽快实名认证！");
            }
        }
    }

    @Override
    public void showErr() {
        ToolUitls.toast(this, "推送未注册成功！");
    }


    public class FragmentAdater extends FragmentPagerAdapter {

        List<Fragment> list;

        public FragmentAdater(FragmentManager fm, List<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int i) {
            return list.get(i);
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            if (BuildConfig.DEBUG) Log.d("MainActivity", "code:" + code);
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";

//                    SharedPreferences.Editor edit = sharedPreferences.edit();
//                    edit.putBoolean(Const.HAD_REGISTER, true);
//                    edit.apply();

                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.d("main", logs);
            }
        }
    };

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("main", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            msg.obj.toString(),
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.d("main", "Unhandled msg - " + msg.what);
            }
        }
    };

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                ToolUitls.toast(getApplicationContext(), "再次点击退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                ActivityStack.getInstance().popAllActivity();
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public class UpdateRealNameStateBroadReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Const.UPDATE_REAL_NAME_STATE.equals(intent.getAction())) {
                mPresenter.showRealName(userId);
            }
        }
    }
}

