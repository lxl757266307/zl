package cn.org.happyhome.library.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;
import java.util.List;

import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.LogUtils;
import cn.org.happyhome.library.utils.StatusBarUtils;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        StatusBarUtils.setStatusBar(this, false, true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }


    public abstract void inited();

    public abstract void initListener();

    public void goOtherActivity(Activity activity, Class cls) {
        Intent intent = new Intent(activity, cls);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ActivityStack.getInstance().removeActivity(this);
    }


    /* 通过监听 触摸事件 动态 隐藏 输入法*/
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    /* 获取 点击的 当前的 view 的 范围 */
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
//            v.setFocusable(true);
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                LogUtils.e(this.getClass().getSimpleName(), new String("测试"));
                return true;
            }
        }
        return false;
    }
}
