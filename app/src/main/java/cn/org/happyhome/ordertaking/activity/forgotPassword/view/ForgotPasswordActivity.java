package cn.org.happyhome.ordertaking.activity.forgotPassword.view;

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
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpActivity;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.RegexUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.ordertaking.R;
import cn.org.happyhome.ordertaking.activity.forgotPassword.contract.IForgotPasswordContract;
import cn.org.happyhome.ordertaking.activity.forgotPassword.presenter.ForgotPasswordPresenter;
import cn.org.happyhome.ordertaking.bean.OrgBean;
import cn.org.happyhome.ordertaking.bean.ResultBean;
import cn.org.happyhome.ordertaking.bean.User;

/**
 * Description :
 *
 * @author 刘祥龙
 * @date 2018/12/12  14:48
 * - generate by MvpAutoCodePlus plugin.
 */

public class ForgotPasswordActivity extends BaseMvpActivity<IForgotPasswordContract.View, IForgotPasswordContract.Presenter> implements IForgotPasswordContract.View {

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
    @BindView(R.id.edit_id_card)
    EditText editIdCard;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.phone_sign_in_button)
    Button phoneSignInButton;
    @BindView(R.id.phone_login_form)
    LinearLayout phoneLoginForm;


    @Override
    public void initListener() {
        setContentView(R.layout.activity_forgotpassword);
        ButterKnife.bind(this);
        txtTitle.setText("重置密码");

    }

    @Override
    public IForgotPasswordContract.Presenter createPresenter() {
        return new ForgotPasswordPresenter();
    }

    @Override
    public IForgotPasswordContract.View createView() {
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
                updatePassword();
                break;
        }
    }

    private void updatePassword() {
        String phone = txtPhone.getText().toString();
        String idCard = editIdCard.getText().toString();
        String password = editPassword.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            ToolUitls.toast(this, "手机号码不得为空");
            txtPhone.requestFocus();
            txtPhone.setError("请填写手机号码");
            return;
        }
        if (!RegexUtils.isMobile(phone)) {
            ToolUitls.toast(this, "手机号码格式错误");
            txtPhone.requestFocus();
            txtPhone.setError("手机号码格式错误");
            return;
        }

        if (TextUtils.isEmpty(idCard)) {
            ToolUitls.toast(this, "身份证号码不得为空");
            editIdCard.requestFocus();
            editIdCard.setError("请填写身份证号码");
            return;
        }
        if (!RegexUtils.isIDCard(idCard)) {
            ToolUitls.toast(this, "身份证号码格式错误");
            editIdCard.requestFocus();
            editIdCard.setError("身份证号码格式错误");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ToolUitls.toast(this, "密码不得为空");
            editPassword.requestFocus();
            editPassword.setError("请填写密码");
            return;
        }
        if (!RegexUtils.isPassword(password)) {
            ToolUitls.toast(this, "密码格式错误");
            editPassword.requestFocus();
            editPassword.setError("密码格式错误");
            return;
        }

        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setIdcard(idCard);
        mPresenter.updatePassword(user);

    }

    @Override
    public void showErr() {
        ToolUitls.toast(this, "服务器繁忙，请稍后再试！");
    }



    @Override
    public void updatePassword(ResultBean<String> resultBean) {
        if (BuildConfig.DEBUG) Log.d("ForgotPasswordActivity", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            ToolUitls.toast(this, "更新密码成功，请重新登陆");
            finish();
        } else if (resultBean.getCode() == Const.REAL_NAME_STATE_9980) {
            ToolUitls.toast(this, "手机号或身份证号不匹配");
        }
    }


}

