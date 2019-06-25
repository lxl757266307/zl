package cn.org.happyhome.ordertaking.activity.register.view;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.RegexUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.library.view.CustomerDialog;
import cn.org.happyhome.nursing.activity.main.view.MainActivity;
import cn.org.happyhome.nursing.activity.realName.view.RealNameActivity;
import cn.org.happyhome.ordertaking.BuildConfig;
import cn.org.happyhome.ordertaking.R;
import cn.org.happyhome.ordertaking.activity.register.contract.IRegisterContract;
import cn.org.happyhome.ordertaking.activity.register.presenter.RegisterPresenter;
import cn.org.happyhome.ordertaking.adapter.MySpinerAdapter;
import cn.org.happyhome.ordertaking.bean.OrgBean;
import cn.org.happyhome.ordertaking.bean.ResultBean;
import cn.org.happyhome.ordertaking.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/11/8  11:36
 * - generate by MvpAutoCodePlus plugin.
 */

public class RegisterActivity extends BaseMvpActivity<IRegisterContract.View, IRegisterContract.Presenter>
        implements IRegisterContract.View, CustomerDialog.OnDialogChildViewClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.txt_back)
    TextView txtBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.txt_commit)
    TextView txtCommit;
    @BindView(R.id.login_progress)
    ProgressBar loginProgress;
    @BindView(R.id.txt_phone)
    AutoCompleteTextView txtPhone;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_password_agin)
    EditText editPasswordAgin;
    @BindView(R.id.phone_sign_in_button)
    Button phoneSignInButton;
    @BindView(R.id.phone_login_form)
    LinearLayout phoneLoginForm;

    CustomerDialog customerDialog;

    SharedPreferences sharedPreferences;
    @BindView(R.id.radio_nusr)
    RadioButton radioNusr;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    /*1护工
      2病人
      3义工
      4小蜜蜂
      5微 信用户*/
    String userType = "1";
    MySpinerAdapter mySpinerAdapter;
    @BindView(R.id.spinner)
    Spinner spinner;

    @Override
    public void initListener() {
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        ActivityStack.getInstance().pushActivity(this);
        sharedPreferences = getSharedPreferences(Const.USER_INFO, MODE_PRIVATE);
        radioGroup.setOnCheckedChangeListener(this);
        txtTitle.setText("注册");
        mPresenter.showOrg();

    }

    @Override
    public IRegisterContract.Presenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public IRegisterContract.View createView() {
        return this;
    }


    @OnClick({R.id.img_back, R.id.phone_sign_in_button, R.id.txt_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.txt_back:
                finish();
                break;
            case R.id.phone_sign_in_button:
                register();
                break;
        }
    }

    private void register() {
        txtPhone.setError(null);
        editPassword.setError(null);
        editPasswordAgin.setError(null);

        String phone = txtPhone.getText().toString();
        String password = editPassword.getText().toString();
        String passwordAgin = editPasswordAgin.getText().toString();


        if (TextUtils.isEmpty(phone)) {
            txtPhone.setError("手机号码不得为空");
            txtPhone.requestFocus();
            return;
        }

        if (BuildConfig.DEBUG)
            Log.d("RegisterActivity", "RegexUtils.isMobile(phone):" + RegexUtils.isMobile(phone));

        if (!RegexUtils.isMobile(phone)) {
            txtPhone.setError("手机号码格式错误");
            txtPhone.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(password)) {
            editPassword.setError("密码不得为空");
            editPassword.requestFocus();
            return;
        }

        if (!RegexUtils.isPassword(password)) {
            editPassword.setError("密码格式错误");
            editPassword.requestFocus();
            return;
        }


        if (TextUtils.isEmpty(passwordAgin)) {
            editPasswordAgin.setError("密码不得为空");
            editPasswordAgin.requestFocus();
            return;
        }

        if (!RegexUtils.isPassword(passwordAgin)) {
            editPasswordAgin.setError("密码格式错误");
            editPasswordAgin.requestFocus();
            return;
        }


        if (!password.equals(passwordAgin)) {
            editPasswordAgin.setError("两次输入密码不一致");
            editPasswordAgin.requestFocus();
            return;
        }
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setUsertypeid(userType);
        mPresenter.register(user, organizationID);
    }

    @Override
    public void showRegister(ResultBean<User> resultBean) {
        if (cn.org.happyhome.library.BuildConfig.DEBUG)
            Log.d("RegisterActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            ToolUitls.toast(this, "注册成功！");
            User data = resultBean.getData();
            String id = data.getId();
            String name = data.getName();
            String usertypeid = data.getUsertypeid();
            String userlevel = data.getUserlevel();
            String headaimage = data.getHeadaimage();
            String password = data.getPassword();
            String phone = data.getPhone();
            String status = data.getStatus();
            //  请假
            String leave = data.getLeave();
            //  1 代表  1 对 1   0 代表多对多
            String oneToOne = data.getOnetone();

            SharedPreferences.Editor edit = sharedPreferences.edit();
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

            String idcard = data.getIdcard();
//            if (idcard == null) {
//                customerDialog = new CustomerDialog(this, "请先进行实名认证，否则无法接单！");
//                customerDialog.setOnDialogChildViewClickListener(this);
//                customerDialog.show();
//            }

//            goOtherActivity(this, MainActivity.class);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
        } else if (resultBean.getCode() == Const.RESULT_HAD_REGISTER) {
            ToolUitls.toast(this, "当前用户已注册！");
            txtPhone.setError("当前用户已注册");
            txtPhone.setText("");
        } else {
            ToolUitls.toast(this, "服务器繁忙,请稍后再试！");
        }
    }

    List<OrgBean> data;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void showOrg(ResultBean<List<OrgBean>> resultBean) {
        if (cn.org.happyhome.library.BuildConfig.DEBUG)
            Log.d("RegisterActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            data = resultBean.getData();
            if (data != null) {
                if (data.size() > 0) {
                    spinner.setDropDownWidth(400); //下拉宽度
//                    spinner.setDropDownHorizontalOffset(100); //下拉的横向偏移
                    spinner.setDropDownVerticalOffset(100); //下拉的纵向偏移
                    mySpinerAdapter = new MySpinerAdapter(data, this);
                    spinner.setAdapter(mySpinerAdapter);
                    spinner.setOnItemSelectedListener(this);
                    organizationID = data.get(0).getOrganizationID();
                }
            }


        }
    }

    @Override
    public void showErr() {
        ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
    }

    @Override
    public void onCancle() {
        if (customerDialog != null) {
            customerDialog.dismiss();

            if (userType.equals("1")) {
                goOtherActivity(this, MainActivity.class);
            }
        }
    }

    @Override
    public void onSure() {
        if (customerDialog != null) {
            customerDialog.dismiss();
            goOtherActivity(this, RealNameActivity.class);
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_nusr:
                userType = "1";//护工
                break;
        }
    }

    String organizationID;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        OrgBean orgBean = data.get(position);
        if (cn.org.happyhome.library.BuildConfig.DEBUG)
            Log.d("RegisterActivity", "orgBean:" + orgBean);
        organizationID = orgBean.getOrganizationID();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}

