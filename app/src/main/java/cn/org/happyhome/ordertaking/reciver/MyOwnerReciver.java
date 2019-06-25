package cn.org.happyhome.ordertaking.reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cn.org.happyhome.nursing.activity.orderDetail.view.OrderHistoryDetailActivity;

public class MyOwnerReciver extends BroadcastReceiver {
    public static final String TAG = "MyOwnerReciver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);

//        SharedPreferences sharedPreferences = context.getSharedPreferences(Const.USER_INFO, Activity.MODE_PRIVATE);
//        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
//            String id = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            if (cn.org.happyhome.library.BuildConfig.DEBUG) Log.d(TAG, "id=====" + id);
//            if (id != null) {
//                SharedPreferences.Editor edit = sharedPreferences.edit();
//                edit.putString(Const.REGISTER_ID, id);
//                edit.putBoolean(Const.HAD_REGISTER, true);
//                edit.apply();
//            }
//        } else
        if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            if (cn.org.happyhome.library.BuildConfig.DEBUG) Log.d(TAG, "点击了");
            openNotification(context, bundle);
        }


    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String orderId = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            if (cn.org.happyhome.library.BuildConfig.DEBUG)
                Log.d(TAG, "extrasJson:" + extrasJson.toString());
            orderId = extrasJson.getString("orderid");
            if (cn.org.happyhome.library.BuildConfig.DEBUG) Log.d(TAG, orderId);
        } catch (Exception e) {
            return;
        }
        Intent mIntent = new Intent(context, OrderHistoryDetailActivity.class);
        mIntent.putExtra("orderId", orderId);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }
}
