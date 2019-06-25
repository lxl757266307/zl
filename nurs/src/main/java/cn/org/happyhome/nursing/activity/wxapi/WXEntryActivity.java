package cn.org.happyhome.nursing.activity.wxapi;/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2013年 mob.com. All rights reserved.
 */


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;

import cn.org.happyhome.library.base.Const;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 微信客户端回调activity示例
 */
public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    public static final String WEIXIN_ACCESS_TOKEN_KEY = "wx_access_token_key";
    public static final String WEIXIN_OPENID_KEY = "wx_openid_key";
    public static final String WEIXIN_REFRESH_TOKEN_KEY = "wx_refresh_token_key";

    public static IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        api = WXAPIFactory.createWXAPI(this, Const.APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    @Override
    public void onReq(BaseReq req) {
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                String code = ((SendAuth.Resp) resp).code;
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this, "分享取消", Toast.LENGTH_LONG).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this, "分享被拒绝", Toast.LENGTH_LONG).show();
                finish();
                break;
            default:
                Toast.makeText(this, "分享返回", Toast.LENGTH_LONG).show();
                break;
        }

    }

    /**
     * 判断accesstoken是过期
     *
     * @param accessToken token
     * @param openid      授权用户唯一标识
     */
    private void isExpireAccessToken(final String accessToken, final String openid) {
        final String url = "https://api.weixin.qq.com/sns/auth?" +
                "access_token=" + accessToken +
                "&openid=" + openid;
        httpRequest(url, new ApiCallback<String>() {
            @Override
            public void onSuccess(String response) {
                boolean flag = validateSuccess(response);
                if (flag) {
                    // accessToken没有过期，获取用户信息
                    getUserInfo(accessToken, openid);
                } else {
                    // 过期了，使用refresh_token来刷新accesstoken
                    refreshAccessToken();
                }
            }

            @Override
            public void onError(int errorCode, final String errorMsg) {
                showMessage("错误信息: " + errorMsg);
            }

            @Override
            public void onFailure(IOException e) {
                showMessage("登录失败");
            }
        });


    }

    /* */

    /**
     * 刷新获取新的access_token
     * <p>
     * /
     */
    private void refreshAccessToken() {
        // 从本地获取以存储的refresh_token
//        SharedPreferences sharedPreferences = getSharedPreferences(Contacts.USER, MODE_PRIVATE);
//        String refresh_token = sharedPreferences.getString(WEIXIN_REFRESH_TOKEN_KEY, null);
//        if (TextUtils.isEmpty(refresh_token)) {
//            return;
//        }
//        // 拼装刷新access_token的url请求地址
//        final String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
//                "appid=" + Contacts.APP_ID +
//                "&grant_type=refresh_token" +
//                "&refresh_token=" + refresh_token;
//        // 请求执行
//        httpRequest(url, new ApiCallback<String>() {
//            @Override
//            public void onSuccess(String response) {
//                // 判断是否获取成功，成功则去获取用户信息，否则提示失败
//                processGetAccessTokenResult(response);
//            }
//
//            @Override
//            public void onError(int errorCode, final String errorMsg) {
//                showMessage("错误信息: " + errorMsg);
//                // 重新请求授权
//                loginWeixin(WXEntryActivity.this.getApplicationContext(), MyApplication.api);
//            }
//
//            @Override
//            public void onFailure(IOException e) {
//                showMessage("登录失败");
//                // 重新请求授权
//                loginWeixin(WXEntryActivity.this.getApplicationContext(), MyApplication.api);
//            }
//        });


    }


    /**
     * 登录微信
     *
     * @param api 微信服务api
     */
    static Gson gson;

    public static void loginWeixin(Context context, IWXAPI api) {
        // 判断是否安装了微信客户端
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context.getApplicationContext(), "您还未安装微信客户端！", Toast.LENGTH_SHORT).show();
            return;
        }
        gson = new Gson();
        // 发送授权登录信息，来获取code
        SendAuth.Req req = new SendAuth.Req();
        // 应用的作用域，获取个人信息
        req.scope = "snsapi_userinfo";
        /**
         * 用于保持请求和回调的状态，授权请求后原样带回给第三方
         * 为了防止csrf攻击（跨站请求伪造攻击），后期改为随机数加session来校验
         */
        req.state = "app_wechat";
        api.sendReq(req);
    }

    /**
     * 获取授权口令
     */
    private void getAccessToken(String code) {
//        final String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
//                "appid=" + Contacts.APP_ID +
//                "&secret=" + Contacts.App_Secret +
//                "&code=" + code +
//                "&grant_type=authorization_code";
//// 网络请求获取access_token
//        httpRequest(url, new ApiCallback<String>() {
//            @Override
//            public void onSuccess(String response) {
//                // 判断是否获取成功，成功则去获取用户信息，否则提示失败
//                processGetAccessTokenResult(response);
//            }
//
//            @Override
//            public void onError(int errorCode, final String errorMsg) {
//                showMessage("错误信息: " + errorMsg);
//            }
//
//            @Override
//            public void onFailure(IOException e) {
//                showMessage("登录失败");
//            }
//        });


    }

    /**
     * 处理获取的授权信息结果
     *
     * @param response 授权信息结果
     */
    private void processGetAccessTokenResult(String response) {
//        // 验证获取授权口令返回的信息是否成功
//        if (validateSuccess(response)) {
//            // 使用Gson解析返回的授权口令信息
//            WXAccessTokenInfo tokenInfo = gson.fromJson(response, WXAccessTokenInfo.class);
//            // 保存信息到手机本地
//            saveAccessInfotoLocation(tokenInfo);
//            // 获取用户信息
//            getUserInfo(tokenInfo.getAccess_token(), tokenInfo.getOpenid());
//        } else {
//            // 授权口令获取失败，解析返回错误信息
//            WXErrorInfo wxErrorInfo = gson.fromJson(response, WXErrorInfo.class);
//            // 提示错误信息
//            showMessage("错误信息: " + wxErrorInfo.getErrmsg());
//
//        }
    }

//    private void saveAccessInfotoLocation(WXAccessTokenInfo tokenInfo) {

//        SharedPreferences sharedPreferences = getSharedPreferences(Contacts.USER, MODE_PRIVATE);
//        SharedPreferences.Editor edit = sharedPreferences.edit();
//        edit.putString(WEIXIN_ACCESS_TOKEN_KEY, tokenInfo.getAccess_token());
//        edit.putString(WEIXIN_REFRESH_TOKEN_KEY, tokenInfo.getRefresh_token());
//        edit.putString(WEIXIN_OPENID_KEY, tokenInfo.getOpenid());
//        edit.commit();

//    }

    private void showMessage(String s) {
//        ToolUitls.toast(this, s);
    }

    private void getUserInfo(String access_token, String openid) {
//        final String url = "https://api.weixin.qq.com/sns/userinfo?" +
//                "access_token=" + access_token +
//                "&openid=" + openid;
//
//        httpRequest(url, new ApiCallback<String>() {
//            @Override
//            public void onSuccess(String response) {
//
//                // 解析获取的用户信息
//                WXUserInfo userInfo = gson.fromJson(response, WXUserInfo.class);
//                Intent intent = new Intent();
//                intent.setAction(Contacts.TI_XIAN);
//
//                intent.putExtra("unionid", userInfo.getUnionid());
//                intent.putExtra("openid", userInfo.getOpenid());
//                sendBroadcast(intent);
//
//                ToolUitls.toast(WXEntryActivity.this, "登录成功");
//            }
//
//            @Override
//            public void onError(int errorCode, String errorMsg) {
//                showMessage("错误信息: " + errorMsg);
//            }
//
//            @Override
//            public void onFailure(IOException e) {
//                showMessage("获取用户信息失败");
//            }
//        });


    }

    /**
     * 验证是否成功
     *
     * @param response 返回消息
     * @return 是否成功
     */
    private boolean validateSuccess(String response) {
        String errFlag = "errmsg";
        return (errFlag.contains(response) && !"ok".equals(response))
                || (!"errcode".contains(response) && !errFlag.contains(response));
    }

    private OkHttpClient mHttpClient = new OkHttpClient.Builder().build();
    private Handler mCallbackHandler = new Handler(Looper.getMainLooper());

    /**
     * 通过Okhttp与微信通信
     * * @param url 请求地址
     *
     * @throws Exception
     */
    public void httpRequest(String url, final ApiCallback<String> callback) {
        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();


        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                if (callback != null) {
                    mCallbackHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 请求失败，主线程回调
                            callback.onFailure(e);
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (callback != null) {
                    if (!response.isSuccessful()) {
                        mCallbackHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                // 请求出错，主线程回调
                                callback.onError(response.code(), response.message());
                            }
                        });
                    } else {
                        mCallbackHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 请求成功，主线程返回请求结果
                                    callback.onSuccess(response.body().string());
                                } catch (final IOException e) {
                                    // 异常出错，主线程回调
                                    mCallbackHandler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            callback.onFailure(e);
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    // Api通信回调接口
    public interface ApiCallback<T> {
        /**
         * 请求成功
         *
         * @param response 返回结果
         */
        void onSuccess(T response);

        /**
         * 请求出错
         *
         * @param errorCode 错误码
         * @param errorMsg  错误信息
         */
        void onError(int errorCode, String errorMsg);

        /**
         * 请求失败
         */
        void onFailure(IOException e);
    }


}
