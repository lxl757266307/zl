package cn.org.happyhome.nursing.activity.setting.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.suke.widget.SwitchButton;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.StatusBarUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.chooseServiceContent.view.ChooseServiceContentActivity;
import cn.org.happyhome.nursing.activity.chooseservice.view.ChooseServiceActivity;
import cn.org.happyhome.nursing.activity.realName.view.RealNameActivity;
import cn.org.happyhome.nursing.activity.setting.contract.ISettingContract;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.nursing.activity.setting.presenter.SettingPresenter;
import cn.org.happyhome.nursing.activity.updatepassword.view.UpdatePasswordActivity;
import cn.org.happyhome.nursing.activity.updatephone.view.UpdatePhoneActivity;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/10/12  9:53
 * - generate by MvpAutoCodePlus plugin.
 */

public class SettingActivity extends BaseMvpActivity<ISettingContract.View, ISettingContract.Presenter> implements ISettingContract.View, View.OnClickListener {

    ImageView imgBack;
    TextView txtTitle;
    SwitchButton switchTakeDay;
    SwitchButton switchManyToMany;
    TextView txtUpdatePassword;
    TextView txtUpdatePhone;
    TextView txtUpdateService;
    TextView txtRealName;
    TextView txtChooseServiceContent;
    TextView txtBack;

    String userId;
    String oneToOne;
    String leave;
    String mangyToMany;
    SharedPreferences sharedPreferences;
    String organizationID;

    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);

        setContentView(R.layout.activity_setting);
        txtTitle = findViewById(R.id.txt_title);
        imgBack = findViewById(R.id.img_back);
        txtBack = findViewById(R.id.txt_back);
        txtRealName = (TextView) findViewById(R.id.txt_real_name);
        txtUpdateService = (TextView) findViewById(R.id.txt_update_service);
        txtUpdatePhone = (TextView) findViewById(R.id.txt_update_phone);
        txtUpdatePassword = (TextView) findViewById(R.id.txt_update_password);
        txtChooseServiceContent = (TextView) findViewById(R.id.txt_service_content);
        switchManyToMany = (SwitchButton) findViewById(R.id.switch_many_to_many);
        switchTakeDay = (SwitchButton) findViewById(R.id.switch_take_day);
        switchManyToMany.setOnCheckedChangeListener(onCheckedChangeListener);
        switchTakeDay.setOnCheckedChangeListener(onCheckedChangeListener);
        imgBack.setOnClickListener(this);
        txtChooseServiceContent.setOnClickListener(this);
        txtUpdatePassword.setOnClickListener(this);
        txtRealName.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        txtUpdatePhone.setOnClickListener(this);
        txtUpdateService.setOnClickListener(this);
        this.txtTitle.setText("设置");
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        leave = sharedPreferences.getString(Const.USER_LEAVE, null);
        oneToOne = sharedPreferences.getString(Const.USER_ONE_TO_ONE, null);
        mangyToMany = sharedPreferences.getString(Const.USER_MANY_TO_MANY, null);
        organizationID = sharedPreferences.getString(Const.ORGANIZATIONID, null);
        if (BuildConfig.DEBUG) Log.d("SettingActivity", "leave==" + leave);
        if (BuildConfig.DEBUG) Log.d("SettingActivity", "oneToOne==" + oneToOne);

        if (leave != null) {
            if (Const.USER_IS_LEAVE.equals(leave)) {
                switchTakeDay.setChecked(true);
            } else if (Const.USER_NOT_LEAVE.equals(leave)) {
                switchTakeDay.setChecked(false);
            }
        }


        if (oneToOne != null && mangyToMany != null) {
            if (Const.USER_IS_MANY.equals(mangyToMany) && Const.USER_NOT_ONE.equals(oneToOne)) {
                switchManyToMany.setChecked(true);
            } else if (Const.USER_IS_ONE.equals(oneToOne) && Const.USER_NOT_MANY.equals(mangyToMany)) {
                switchManyToMany.setChecked(false);
            }
        }
    }

    @Override
    public ISettingContract.Presenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    public ISettingContract.View createView() {
        return this;
    }


    SwitchButton.OnCheckedChangeListener onCheckedChangeListener = new SwitchButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(SwitchButton view, boolean isChecked) {
            User user = new User();
            user.setId(userId);
            if (BuildConfig.DEBUG) Log.d("SettingActivity", "isChecked:" + isChecked);
            if (view.getId() == R.id.switch_many_to_many) {
                if (isChecked) {
                    user.setManytomany(Const.USER_IS_MANY);
                    user.setOnetone(Const.USER_NOT_ONE);
                    mPresenter.updateUserInfo(user, organizationID);
                } else {
                    user.setOnetone(Const.USER_IS_ONE);
                    user.setManytomany(Const.USER_NOT_MANY);
                    mPresenter.updateUserInfo(user, organizationID);
                }
            } else if (view.getId() == R.id.switch_take_day) {
                if (isChecked) {
                    user.setLeave(Const.USER_IS_LEAVE);
                    mPresenter.updateUserInfo(user, organizationID);
                } else {
                    user.setLeave(Const.USER_NOT_LEAVE);
                    mPresenter.updateUserInfo(user, organizationID);
                }
            }
        }
    };

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.img_back) {
            finish();
        } else if (view.getId() == R.id.txt_back) {
            finish();
        } else if (view.getId() == R.id.txt_update_password) {
            goOtherActivity(this, UpdatePasswordActivity.class);

        } else if (view.getId() == R.id.txt_update_phone) {
            goOtherActivity(this, UpdatePhoneActivity.class);

        } else if (view.getId() == R.id.txt_update_service) {
            goOtherActivity(this, ChooseServiceActivity.class);
        } else if (view.getId() == R.id.txt_real_name) {

//            goOtherActivity(this, RealNameActivity.class);
            mPresenter.getUserInfo(userId);

        } else if (view.getId() == R.id.txt_service_content) {
            goOtherActivity(this, ChooseServiceContentActivity.class);
        }


    }

    @Override
    public void updateUserInfo(ResultBean<User> resultBean) {
        updateInfo(resultBean);
    }

    @Override
    public void getUserInfo(ResultBean<User> resultBean) {
        if (cn.org.happyhome.nursing.BuildConfig.DEBUG)
            Log.d("SettingActivity", "resultBean:" + resultBean);
        updateInfo(resultBean);
        String state = sharedPreferences.getString(Const.REAL_NAME_STATE, null);
        if (Const.REAL_NAME_STATE_9981.equals(state)) {
            ToolUitls.toast(this, "实名认证未通过，请重新认证！");
            goOtherActivity(this, RealNameActivity.class);
        } else if (Const.REAL_NAME_STATE_9982.equals(state)) {
            goOtherActivity(this, RealNameActivity.class);
        } else if (Const.REAL_NAME_STATE_9983.equals(state)) {
            ToolUitls.toast(this, "审核中，请耐心等待！");
        } else if (Const.REAL_NAME_STATE_9987.equals(state)) {
            ToolUitls.toast(this, "已实名认证，无需再次申请！");
        }
    }

    private void updateInfo(ResultBean<User> resultBean) {
        if (resultBean.getCode() == Const.RESULT_OK) {
            SharedPreferences.Editor edit = sharedPreferences.edit();
            User data = resultBean.getData();
            String id = data.getId();
            String name = data.getName();
            String usertypeid = data.getUsertypeid();
            String userlevel = data.getUserlevel();
            String headaimage = data.getHeadaimage();
            String password = data.getPassword();
            String phone = data.getPhone();
            String status = data.getStatus();
            String onetone = data.getOnetone();
            String manytomany = data.getManytomany();
            //  请假
            String leave = data.getLeave();
            //  1 代表  1 对 1   0 代表多对多
            String oneToOne = data.getOnetone();

            if (BuildConfig.DEBUG) Log.d("SettingActivity", "oneToOne222=" + oneToOne);

            edit.putString(Const.USER_ONE_TO_ONE, onetone);
            edit.putString(Const.USER_MANY_TO_MANY, manytomany);
            edit.putString(Const.USER_NAME, name);
            edit.putString(Const.USER_ONE_TO_ONE, oneToOne);
            edit.putString(Const.USER_LEAVE, leave);
            edit.putString(Const.USER_ONLINE, status);
            edit.putString(Const.USER_TYPE, usertypeid);
            edit.putString(Const.USER_ID, id);
            edit.putString(Const.USER_PASSWORD, password);
            edit.putString(Const.USER_IMG, headaimage);
            edit.putString(Const.USER_LEVEL, userlevel);
            edit.putString(Const.USER_PHONE, phone);
            edit.apply();


        } else {
            ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
        }
    }

    @Override
    public void showErr(String msg) {
        ToolUitls.toast(this, msg);
    }

}

