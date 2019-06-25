package cn.org.happyhome.nursing.activity.updatepassword.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.StatusBarUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.BuildConfig;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.updatepassword.contract.IUpdatePasswordContract;
import cn.org.happyhome.nursing.activity.updatepassword.presenter.UpdatePasswordPresenter;
import cn.org.happyhome.nursing.activity.updatephone.view.UpdatePhoneActivity;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;
import cn.org.happyhome.nursing.utils.UserUtils;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/25  16:33
 * - generate by MvpAutoCodePlus plugin.
 */

public class UpdatePasswordActivity extends BaseMvpActivity<IUpdatePasswordContract.View, IUpdatePasswordContract.Presenter>
        implements IUpdatePasswordContract.View, View.OnClickListener {

    SharedPreferences sharedPreferences;
    ImageView imgBack;
    TextView txtTitle;
    AutoCompleteTextView editPhone;
    EditText editOlPassword;
    EditText editNewPassword;
    Button phoneSignInButton;
    LinearLayout phoneLoginForm;

    String phone;
    String oldPassword;
    String newPassword;
    TextView txtBack;
    String userId;
    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);

        setContentView(R.layout.activity_update_password);
        this.imgBack = findViewById(R.id.img_back);
        this.txtTitle = findViewById(R.id.txt_title);
        this.txtBack = findViewById(R.id.txt_back);
        this.txtTitle.setText("修改密码");
        this.phoneLoginForm = (LinearLayout) findViewById(R.id.phone_login_form);
        this.phoneSignInButton = (Button) findViewById(R.id.phone_sign_in_button);
        this.editNewPassword = (EditText) findViewById(R.id.edit_new_password);
        this.editOlPassword = (EditText) findViewById(R.id.edit_ol_password);
        this.editPhone = (AutoCompleteTextView) findViewById(R.id.edit_phone);
        imgBack.setOnClickListener(this);
        txtBack.setOnClickListener(this);
        phoneSignInButton.setOnClickListener(this);
        StatusBarUtils.setStatusBar(this, false, true);
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);

    }

    @Override
    public IUpdatePasswordContract.Presenter createPresenter() {
        return new UpdatePasswordPresenter();
    }

    @Override
    public IUpdatePasswordContract.View createView() {
        return this;
    }

    @Override
    public void updateUserInfoSuccess(ResultBean<User> resultBean) {
        UserUtils.updateUserInfo(resultBean, sharedPreferences);
        ToolUitls.toast(this, "更新密码成功！");
       mPresenter.loginOut(new User(userId,"0"));
    }

    @Override
    public void loginOut(ResultBean<User> userResultBean) {
        if (userResultBean.getCode() == Const.RESULT_OK) {
            sharedPreferences.edit().clear().apply();
            Class clz = null;
            try {
                clz = Class.forName("cn.org.happyhome.ordertaking.activity.login.view.LoginActivity");
                Intent intent = new Intent(UpdatePasswordActivity.this, clz);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                ActivityStack.getInstance().popAllActivity();
                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(Const.USER_ONLINE, Const.ONLINE);
                edit.apply();
                startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void updateUserInfoFailed(String msg) {
        ToolUitls.toast(this, msg);
    }

    @Override
    public void showErr() {

    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.img_back) {
            finish();
        }
        if (view.getId() == R.id.txt_back) {
            finish();
        }
        if (view.getId() == R.id.phone_sign_in_button) {
            if (!checkContent()) {
                return;
            }
            if (!checkNull()) {
                return;
            }
            if (!checkPhone()) {
                return;
            }
            if (BuildConfig.DEBUG) Log.d("UpdatePasswordActivity", "---");
            User user = new User();
            String id = sharedPreferences.getString(Const.USER_ID, null);
            user.setId(id);
            user.setPassword(newPassword);
            String organizationID = sharedPreferences.getString(Const.ORGANIZATIONID, null);
            mPresenter.updateUserInfo(user, organizationID);
        }


    }

    private boolean checkPhone() {
        String phone = sharedPreferences.getString(Const.USER_PHONE, null);
        String password = sharedPreferences.getString(Const.USER_PASSWORD, null);
        if (!phone.equals(phone)) {
            editPhone.setError("手机号码与当前账号不匹配");
            ToolUitls.toast(this, "手机号码与当前账号不匹配");
            return false;
        }
        if (password.equals(newPassword)) {
            editNewPassword.setError("密码有误");
            ToolUitls.toast(this, "密码有误");
            return false;
        }

        return true;
    }

    private boolean checkNull() {
        if (phone == null) {
            editPhone.setError("必填");
            editPhone.requestFocus();
            ToolUitls.toast(UpdatePasswordActivity.this, "手机号码不得为空");
            return false;
        }

        if (oldPassword == null) {
            editOlPassword.setError("必填");
            editOlPassword.requestFocus();
            ToolUitls.toast(UpdatePasswordActivity.this, "密码不得为空");
            return false;
        }

        if (newPassword == null) {
            editNewPassword.setError("必填");
            editNewPassword.requestFocus();
            ToolUitls.toast(UpdatePasswordActivity.this, "密码不得为空");
            return false;
        }
        return true;
    }

    private boolean checkContent() {
        phone = editPhone.getText().toString();
        oldPassword = editOlPassword.getText().toString();
        newPassword = editNewPassword.getText().toString();

        if (phone != null) {
            if (BuildConfig.DEBUG)
                Log.d("UpdatePasswordActivity", "isPhoneValid(phone):" + isPhoneValid(phone));
            if (!isPhoneValid(phone)) {
                editPhone.setError("手机号码格式错误");
                editPhone.requestFocus();
                ToolUitls.toast(UpdatePasswordActivity.this, "手机号码格式错误");
                return false;
            }
        }

        if (oldPassword != null) {
            if (!oldPassword.equals(sharedPreferences.getString(Const.USER_PASSWORD, null))) {
                editOlPassword.requestFocus();
                editOlPassword.setError("旧密码验证错误11");
                ToolUitls.toast(UpdatePasswordActivity.this, "旧密码验证错误111");
                return false;
            }
        }

        if (cn.org.happyhome.library.BuildConfig.DEBUG) Log.d("UpdatePasswordActivity", "代码有误");

        if (BuildConfig.DEBUG) Log.d("UpdatePasswordActivity", newPassword);
        if (newPassword != null) {
            if (BuildConfig.DEBUG)
                Log.d("UpdatePasswordActivity", "isPasswordType(newPassword):" + isPasswordType(newPassword));
            if (!isPasswordType(newPassword)) {
                editNewPassword.setError("密码号码格式错误");
                editNewPassword.requestFocus();
                ToolUitls.toast(UpdatePasswordActivity.this, "密码格式错误");
                return false;
            }
            if (BuildConfig.DEBUG)
                Log.d("UpdatePasswordActivity", "isPasswordValid(newPassword):" + isPasswordValid(newPassword));
            if (isPasswordValid(newPassword)) {
                editNewPassword.setError("密码长度不得小于4位字符");
                editNewPassword.requestFocus();
                ToolUitls.toast(UpdatePasswordActivity.this, "密码长度不得小于4位字符");
                return false;
            }
        }
        return true;
    }

    private boolean isPhoneValid(String phone) {
        return isPhone(phone);
    }

    private boolean isPasswordValid(String password) {
        return password.length() < 4;
    }

    private boolean isPasswordType(String password) {
        return isLetterDigit(password);
    }

    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;
        //定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;
        //定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {
                //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]{6,12}$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    public boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }


}

