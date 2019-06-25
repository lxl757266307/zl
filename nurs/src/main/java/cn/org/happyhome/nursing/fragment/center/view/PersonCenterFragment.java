package cn.org.happyhome.nursing.fragment.center.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

import cn.org.happyhome.library.BuildConfig;
import cn.org.happyhome.library.base.BaseMvpFragment;
import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.library.utils.ActivityStack;
import cn.org.happyhome.library.utils.PhotoUtils;
import cn.org.happyhome.library.utils.StatusBarUtils;
import cn.org.happyhome.library.utils.ToolUitls;
import cn.org.happyhome.library.view.CustomerDialog;
import cn.org.happyhome.nursing.R;
import cn.org.happyhome.nursing.activity.addAddress.view.AddAddressActivity;
import cn.org.happyhome.nursing.activity.address.view.AddressActivity;
import cn.org.happyhome.nursing.activity.integral.view.IntegrealActivity;
import cn.org.happyhome.nursing.activity.orderHistory.view.OrderHistoryActivity;
import cn.org.happyhome.nursing.activity.setting.view.SettingActivity;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;
import cn.org.happyhome.nursing.fragment.center.contract.IPersonCenterContract;
import cn.org.happyhome.nursing.fragment.center.presenter.PersonCenterPresenter;
import de.hdodenhof.circleimageview.CircleImageView;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Description :
 *
 * @author long
 * @date 2018/8/27 0027  16:50
 * - generate by MvpAutoCodePlus plugin.
 */

public class PersonCenterFragment extends BaseMvpFragment<IPersonCenterContract.View, IPersonCenterContract.Presenter> implements IPersonCenterContract.View, View.OnClickListener, CustomerDialog.OnDialogChildViewClickListener {

    public static final int CHOOSE_PHOTO = 1;

    CircleImageView imgHeader;
    LinearLayout layoutJifen;
    LinearLayout layoutAddress;
    LinearLayout layoutOrder;
    LinearLayout layoutSetting;
    LinearLayout layoutLoginOut;
    TextView txtName;
    RatingBar ratingBar;
    ProgressBar uploading;

    SharedPreferences sharedPreferences;
    String userId;
    String level;

    CustomerDialog loginDialog;

    @Override
    public View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_person_center, container, false);
        layoutSetting = (LinearLayout) rootView.findViewById(R.id.layout_setting);
        uploading = rootView.findViewById(R.id.upload_progress);
        layoutOrder = (LinearLayout) rootView.findViewById(R.id.layout_order);
        layoutAddress = (LinearLayout) rootView.findViewById(R.id.layout_address);
        layoutJifen = (LinearLayout) rootView.findViewById(R.id.layout_jifen);
        layoutLoginOut = (LinearLayout) rootView.findViewById(R.id.layout_login_out);
        ratingBar = (RatingBar) rootView.findViewById(R.id.rating_bar);
        imgHeader = (CircleImageView) rootView.findViewById(R.id.img_header);
        txtName = rootView.findViewById(R.id.txt_name);
        layoutSetting.setOnClickListener(this);
        layoutOrder.setOnClickListener(this);
        layoutLoginOut.setOnClickListener(this);
        layoutAddress.setOnClickListener(this);
        layoutJifen.setOnClickListener(this);
        imgHeader.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void viewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        StatusBarUtils.setStatusBar(getActivity(), false, true);
        if (BuildConfig.DEBUG) Log.d("PersonCenterFragment", "mPresenter:" + mPresenter);
        sharedPreferences = getActivity().getSharedPreferences(Const.USER_INFO, Context.MODE_PRIVATE);
        userId = sharedPreferences.getString(Const.USER_ID, null);
        level = sharedPreferences.getString(Const.USER_LEVEL, null);
        String name = sharedPreferences.getString(Const.USER_NAME, null);
        String headImage = sharedPreferences.getString(Const.USER_IMG, null);
        String online = sharedPreferences.getString(Const.USER_ONLINE, null);
        if (BuildConfig.DEBUG) Log.d("PersonCenterFragment", "headImage=" + headImage);
        if (headImage != null) {
            Glide.with(getActivity()).load(headImage).into(imgHeader);
        } else {
            imgHeader.setImageResource(R.mipmap.headimg);
        }
        if (name != null) {
            txtName.setText(name);
        }
        if (level != null) {
            if (BuildConfig.DEBUG) Log.d("PersonCenterFragment", "levellevel===" + level);
            ratingBar.setRating(Integer.parseInt(level));
        }
        if (userId != null) {
            mPresenter.showUserInfo(userId);
        }

    }

    @Override
    public IPersonCenterContract.Presenter createPresenter() {
        return new PersonCenterPresenter();
    }

    @Override
    public IPersonCenterContract.View createView() {
        return this;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClick(View view) {
        if (view.getId() == R.id.img_header) {
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, CHOOSE_PHOTO);
        } else if (view.getId() == R.id.layout_jifen) {
            Intent intent = new Intent(getActivity(), IntegrealActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.layout_address) {
            Intent intent = new Intent(getActivity(), AddressActivity.class);
//            Intent intent = new Intent();
//            try {
//                Class<?> clazz = Class.forName("cn.org.happyhome.ordertaking.activity.addAddress2.AddAddress2Activity");
//                intent.setClass(getActivity(), clazz);
//            } catch(Exception e){
//                e.printStackTrace();
//            }
            startActivity(intent);
        } else if (view.getId() == R.id.layout_order) {
            Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.layout_setting) {
            Intent intent = new Intent(getActivity(), SettingActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.layout_login_out) {
            loginDialog = new CustomerDialog(getActivity(), "确认退出？");
            loginDialog.setOnDialogChildViewClickListener(this);
            loginDialog.show();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String path = PhotoUtils.getPathFromUri(data, getActivity());
            Log.d("PersonCenterFragment", path);
//            imgHeader.setImageURI(data.getData());
            String cachePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            Luban.with(getActivity())
                    .load(path)
                    .ignoreBy(100)
                    .setTargetDir(cachePath)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            uploading.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onSuccess(File file) {
                            if (BuildConfig.DEBUG)
                                if (BuildConfig.DEBUG)
                                    Log.d("PersonCenterFragment", "file.getAbsoluteFile():" + file.getAbsoluteFile());
                            if (BuildConfig.DEBUG) Log.d("PersonCenterFragment", file.getName());
                            Log.d("PersonCenterFragment", "file.length():" + file.length());
                            // TODO 压缩成功后调用，返回压缩后的图片文件
                            mPresenter.updateUserPhoto(userId, file);

                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            uploading.setVisibility(View.GONE);
                            if (BuildConfig.DEBUG) Log.d("PersonCenterFragment", "e:" + e);
                        }
                    }).launch();

        }
    }

    @Override
    public void showUserInfo(ResultBean<User> result) {
        if (BuildConfig.DEBUG) Log.d("PersonCenterFragment", "result11:" + result);
        if (result.getCode() == Const.RESULT_OK) {
            User data = result.getData();
            txtName.setText(data.getPhone());
            if (data.getLeave() != null) {
                if (BuildConfig.DEBUG)
                    Log.d("PersonCenterFragment", "data.getLeave()===" + data.getLeave());
                ratingBar.setRating(Integer.parseInt(level));
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Const.USER_IMG, data.getHeadaimage());
            edit.putString(Const.USER_NAME, data.getName());
            edit.putString(Const.ORGANIZATIONID, result.getMessage());
            edit.apply();

        }
    }

    @Override
    public void showError(String err) {
        uploading.setVisibility(View.GONE);
        ToolUitls.toast(getActivity(), "服务器繁忙,请稍后再试！！");
    }

    @Override
    public void loginOut(ResultBean<User> resultBean) {
        if (resultBean.getCode() == Const.RESULT_OK) {
            ToolUitls.toast(getActivity(), "退出成功,请重新登陆!");
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Const.USER_ONLINE, resultBean.getData().getStatus());
            edit.apply();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ActivityStack.getInstance().popAllActivity();
                    try {
                        Class clz = Class.forName("cn.org.happyhome.ordertaking.activity.login.view.LoginActivity");
                        startActivity(new Intent(getActivity(), clz));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }, 1000);
        }
    }

    @Override
    public void updateUserPhoto(ResultBean<User> resultBean) {
        if (BuildConfig.DEBUG) Log.d("PersonCenterFragment", "resultBean:" + resultBean);
        if (resultBean.getCode() == Const.RESULT_OK) {
            uploading.setVisibility(View.GONE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(Const.USER_IMG, resultBean.getData().getHeadaimage());
            edit.apply();
            Glide.with(getActivity()).load(resultBean.getData().getHeadaimage()).into(imgHeader);
        } else if (resultBean.getCode() == Const.RESULT_9999) {
            uploading.setVisibility(View.GONE);
            ToolUitls.toast(getActivity(), "服务器繁忙，请稍后再试！ ");
        }
    }

    @Override
    public void onCancle() {
        if (loginDialog != null) {
            if (loginDialog.isShowing()) {
                loginDialog.dismiss();
            }
        }
    }

    @Override
    public void onSure() {
        if (loginDialog != null) {
            if (loginDialog.isShowing()) {
                loginDialog.dismiss();
                //    0代表退出
                if (userId != null) {
                    mPresenter.showLoginOut(new User(userId, "0"));
                }

            }
        }
    }
}

