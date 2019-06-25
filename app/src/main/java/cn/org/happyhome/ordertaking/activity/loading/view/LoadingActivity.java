package cn.org.happyhome.ordertaking.activity.loading.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;


import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.nursing.activity.main.view.MainActivity;
import cn.org.happyhome.ordertaking.R;
import cn.org.happyhome.ordertaking.activity.guid.view.GuidActivity;
import cn.org.happyhome.ordertaking.activity.login.view.LoginActivity;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/22 0022  11:03
 * - generate by MvpAutoCodePlus plugin.
 */

public class LoadingActivity extends Activity {

    Handler handler;
    String online;
    String userType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        boolean isFirst = sharedPreferences.getBoolean(Const.IS_FIRST, false);
        if (!isFirst) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean(Const.IS_FIRST, true);
            edit.apply();
            goOtherActivity(this, GuidActivity.class);
            finish();
        } else {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_loading);
            online = sharedPreferences.getString(Const.USER_ONLINE, null);
            userType = sharedPreferences.getString(Const.USER_TYPE, null);
            if (BuildConfig.DEBUG) Log.d("LoadingActivity", "online:" + online);
            if (BuildConfig.DEBUG) Log.d("LoadingActivity", "userType=" + userType);
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if ("1".equals(online)) {
                        if (userType != null) {
                            if ("1".equals(userType)) {
                                goOtherActivity(LoadingActivity.this, MainActivity.class);
                            }
                        }
                    } else {
                        goOtherActivity(LoadingActivity.this, LoginActivity.class);
                    }
                    finish();
                }
            }, 2000);
        }

    }


    public void goOtherActivity(Activity activity, Class cls) {
        Intent intent = new Intent(activity, cls);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler = null;
        }
    }
}

