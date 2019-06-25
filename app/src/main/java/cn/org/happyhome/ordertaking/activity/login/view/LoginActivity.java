package cn.org.happyhome.ordertaking.activity.login.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.TextUtils;
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

import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.StatusBarUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.library.view.CustomerDialog;
import cn.org.happyhome.nursing.activity.main.view.MainActivity;
import cn.org.happyhome.ordertaking.BuildConfig;
import cn.org.happyhome.ordertaking.R;
import cn.org.happyhome.ordertaking.activity.forgotPassword.view.ForgotPasswordActivity;
import cn.org.happyhome.ordertaking.activity.login.contract.ILoginContract;
import cn.org.happyhome.ordertaking.activity.login.presenter.LoginPresenter;
import cn.org.happyhome.ordertaking.activity.register.view.RegisterActivity;
import cn.org.happyhome.ordertaking.bean.ResultBean;
import cn.org.happyhome.ordertaking.bean.User;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/23 0023  15:49
 * - generate by MvpAutoCodePlus plugin.
 */

public class LoginActivity extends BaseMvpActivity<ILoginContract.View, ILoginContract.Presenter>
        implements ILoginContract.View, View.OnClickListener, CustomerDialog.OnDialogChildViewClickListener {
    // UI references.
    private AutoCompleteTextView mPhoneView;
    private EditText mPasswordView;
    private TextView mTitle;
    private View mProgressView;
    private SharedPreferences sharedPreferences;
    private TextView txtRegister;
    private TextView txtFindBack;

    @Override
    public void initListener() {
        ActivityStack.getInstance().pushActivity(this);
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
//        String status = sharedPreferences.getString(Const.USER_ONLINE, null);
//        if (cn.org.happyhome.library.BuildConfig.DEBUG) Log.d("LoginActivity","status"+ status);
//        if (status != null) {
//            if (Const.ONLINE.equals(status)) {
//                goOtherActivity(this, MainActivity.class);
//            } else if (Const.OFFLINE.equals(status)) {
//                initView();
//            }
//        } else {
//        }
        initView();

    }

    private void initView() {
        StatusBarUtils.setStatusBar(this, false, true);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mPhoneView = (AutoCompleteTextView) findViewById(R.id.email);
        mTitle = (TextView) findViewById(R.id.txt_title);
        txtFindBack = findViewById(R.id.txt_find_back);
        txtRegister = (TextView) findViewById(R.id.txt_register);
        txtRegister.setOnClickListener(this);
        txtFindBack.setOnClickListener(this);
        mTitle.setText("登录");
        mPasswordView = (EditText) findViewById(R.id.password);
//        点击软键盘 回车键触发
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
//
        Button mEmailSignInButton = (Button) findViewById(R.id.phone_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
//
//        mProgressView = findViewById(R.id.login_progress);

    }

    @Override
    public ILoginContract.Presenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public ILoginContract.View createView() {
        return this;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mPhoneView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        // Check for a valid phone address.
        if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_field_required));
            focusView = mPhoneView;
            cancel = true;
        } else if (!isPhoneValid(phone)) {
            mPhoneView.setError(getString(R.string.error_invalid_phone));
            focusView = mPhoneView;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
//        else if (!isPasswordType(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_type_password));
//            focusView = mPasswordView;
//            cancel = true;
//        }

        if (cancel) {
            focusView.requestFocus();
            return;
        }


        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        mPresenter.login(user);
    }


    private boolean isPhoneValid(String phone) {
        //TODO: Replace this with your own logic

        return isPhone(phone);
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
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
        String regex = "^((1[0-9][0-9])|(14[0-9])|(13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    @Override
    public void loginSuccess(ResultBean<User> resultBean) {


//                1	护工
//                2	病人
//                3	义工
//                4	小蜜蜂
//                5	用户
//                usertypeid

        switch (resultBean.getCode()) {
            case 0:
                ToolUitls.toast(this, "登录成功");
                if (BuildConfig.DEBUG) Log.d("LoginActivity", "resultBean:" + resultBean);

                User data = resultBean.getData();
                String id = data.getId();
                String name = data.getName();
                String usertypeid = data.getUsertypeid();
                String userlevel = data.getUserlevel();
                String headaimage = data.getHeadaimage();
                String password = data.getPassword();
                String onetone = data.getOnetone();
                String manytomany = data.getManytomany();
                String organizationid = resultBean.getMessage();
                String phone = data.getPhone();
                String status = data.getStatus();
                if (cn.org.happyhome.library.BuildConfig.DEBUG)
                    Log.d("LoginActivity", "status==" + status);
                //  请假
                String leave = data.getLeave();
                //  1 代表  1 对 1   0 代表多对多
                String oneToOne = data.getOnetone();

                SharedPreferences.Editor edit = sharedPreferences.edit();
                edit.putString(Const.USER_NAME, name);
                edit.putString(Const.ORGANIZATIONID, organizationid);
                edit.putString(Const.USER_ONE_TO_ONE, onetone);
                edit.putString(Const.USER_MANY_TO_MANY, manytomany);
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
                goOtherActivity(this, MainActivity.class);
                finish();


//                switch (usertypeid) {
//                    case "1":
//                        break;
//                    case "2":
//                        break;
//                    case "3":
//                        break;
//                    case "4":
//                        break;
//                    case "5":
//                        break;
//                }


                break;
            case 3001:
                mPhoneView.setText("");
                mPasswordView.setText("");
                ToolUitls.toast(this, "用户不存在");
                break;
            case 3002:
                mPasswordView.setText("");
                mPasswordView.setError("密码错误");
                ToolUitls.toast(this, "密码错误");
                break;
        }
    }

    @Override
    public void loginFaild(String msg) {
        ToolUitls.toast(this, msg);
    }


    CustomerDialog customerDialog;

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_register) {
            goOtherActivity(this, RegisterActivity.class);
        } else if (v.getId() == R.id.txt_find_back) {

            goOtherActivity(this, ForgotPasswordActivity.class);
//            customerDialog = new CustomerDialog(this, "确认联系客服？");
//            customerDialog.setOnDialogChildViewClickListener(this);
//            customerDialog.show();
        }
    }

    @Override
    public void onCancle() {
        if (customerDialog != null) {
            if (customerDialog.isShowing()) {
                customerDialog.dismiss();
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onSure() {
        if (customerDialog != null) {
            if (customerDialog.isShowing()) {
                customerDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:18309225902"));
                startActivity(intent);
            }
        }
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

}

