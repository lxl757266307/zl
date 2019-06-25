package cn.org.happyhome.nursing.activity.updatephone.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.StatusBarUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.updatephone.contract.IUpdatePhoneContract;
import cn.org.happyhome.nursing.activity.updatephone.presenter.UpdatePhonePresenter;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;
import cn.org.happyhome.nursing.utils.UserUtils;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/9/25  15:19
 * - generate by MvpAutoCodePlus plugin.
 */

public class UpdatePhoneActivity extends BaseMvpActivity<IUpdatePhoneContract.View, IUpdatePhoneContract.Presenter>
        implements IUpdatePhoneContract.View, View.OnClickListener {


    @Override
    public IUpdatePhoneContract.Presenter createPresenter() {
        return new UpdatePhonePresenter();
    }

    @Override
    public IUpdatePhoneContract.View createView() {
        return this;
    }


    ImageView imgBack;
    TextView txtTitle;
    AutoCompleteTextView editOldPhone;
    EditText editNewPhone;
    EditText editPassword;
    Button phoneSignInButton;
    TextView txtBack;
    SharedPreferences sharedPreferences;

    String oldPhone;
    String newPhone;
    String password;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_back) {
            this.finish();
        }
        if (view.getId() == R.id.txt_back) {
            this.finish();
        }
        if (view.getId() == R.id.phone_sign_in_button) {

            if (BuildConfig.DEBUG) Log.d("UpdatePhoneActivity", "checkContent():" + checkContent());
            if (!checkContent()) {
                return;
            }
            if (BuildConfig.DEBUG) Log.d("UpdatePhoneActivity", "checkNull():" + checkNull());
            if (!checkNull()) {
                return;
            }
            if (BuildConfig.DEBUG) Log.d("UpdatePhoneActivity", "checkPhone():" + checkPhone());
            if (!checkPhone()) {
                return;
            }
            User user = new User();
            String id = sharedPreferences.getString(Const.USER_ID, null);
            user.setId(id);
            user.setPhone(newPhone);
            String organizationID = sharedPreferences.getString(Const.ORGANIZATIONID, null);
            mPresenter.updateUserInfo(user, organizationID);
        }


    }

    private boolean checkPhone() {
        String phone = sharedPreferences.getString(Const.USER_PHONE, null);
        if (!oldPhone.equals(phone)) {
            editOldPhone.setError("旧手机号码与当前账号不匹配");
            editOldPhone.requestFocus();
            ToolUitls.toast(this, "旧手机号码与当前账号不匹配");
            return false;
        }
        return true;
    }

    private boolean checkNull() {
        if (oldPhone == null) {
            editOldPhone.setError("必填");
            editOldPhone.requestFocus();
            ToolUitls.toast(UpdatePhoneActivity.this, "手机号码不得为空");
            return false;
        }

        if (newPhone == null) {
            editNewPhone.setError("必填");
            editNewPhone.requestFocus();
            ToolUitls.toast(UpdatePhoneActivity.this, "手机号码不得为空");
            return false;
        }

        if (password == null) {
            editPassword.setError("必填");
            editPassword.requestFocus();
            ToolUitls.toast(UpdatePhoneActivity.this, "密码不得为空");
            return false;
        }

        return true;
    }

    String userId;

    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);
        StatusBarUtils.setStatusBar(this, false, true);
        setContentView(R.layout.activity_update_phone);
        this.imgBack = findViewById(R.id.img_back);
        this.txtBack = findViewById(R.id.txt_back);
        this.txtTitle = findViewById(R.id.txt_title);
        this.phoneSignInButton = (Button) findViewById(R.id.phone_sign_in_button);
        this.phoneSignInButton.setOnClickListener(this);
        this.imgBack.setOnClickListener(this);
        this.txtBack.setOnClickListener(this);
        this.txtTitle.setText("修改手机号码");
        this.editPassword = (EditText) findViewById(R.id.edit_password);
        this.editNewPhone = (EditText) findViewById(R.id.edit_new_phone);
        this.editOldPhone = (AutoCompleteTextView) findViewById(R.id.edit_old_phone);
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        editPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    checkContent();
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void updateUserInfoSuccess(ResultBean<User> userResultBean) {
        ToolUitls.toast(this, "更新手机号成功");
//        UserUtils.updateUserInfo(userResultBean, sharedPreferences);
        mPresenter.loginOut(new User(userId, "0"));
    }

    @Override
    public void loginOut(ResultBean<User> userResultBean) {
        if (BuildConfig.DEBUG)
            Log.d("UpdatePhoneActivity", "userResultBean.getCode():" + userResultBean.getCode());
        sharedPreferences.edit().clear().apply();
        Class clz = null;
        try {
            clz = Class.forName("cn.org.happyhome.ordertaking.activity.login.view.LoginActivity");
            Intent intent = new Intent(UpdatePhoneActivity.this, clz);
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


    @Override
    public void updateUserInfoFailed(String msg) {
        ToolUitls.toast(this, msg);
    }

    @Override
    public void showErr() {

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


    private boolean checkContent() {
        oldPhone = editOldPhone.getText().toString();
        newPhone = editNewPhone.getText().toString();
        password = editPassword.getText().toString();

        if (oldPhone != null) {
            if (!isPhoneValid(oldPhone)) {
                editOldPhone.setError("手机号码格式错误");
                editOldPhone.requestFocus();
                ToolUitls.toast(UpdatePhoneActivity.this, "手机号码格式错误");
                return false;
            }
        }

        if (newPhone != null) {
            if (!isPhoneValid(newPhone)) {
                editNewPhone.setError("手机号码格式错误");
                editNewPhone.requestFocus();
                ToolUitls.toast(UpdatePhoneActivity.this, "手机号码格式错误");
                return false;
            }
        }

        if (password != null) {
            if (!isPasswordType(password)) {
                editPassword.setError("密码格式错误");
                editPassword.requestFocus();
                ToolUitls.toast(UpdatePhoneActivity.this, "密码格式错误");
                return false;
            }
            if (isPasswordValid(password)) {
                editPassword.setError("密码格长度不得小于4位字符");
                editPassword.requestFocus();
                ToolUitls.toast(UpdatePhoneActivity.this, "密码格长度不得小于4位字符");
                return false;
            }
        }
        return true;
    }
}

