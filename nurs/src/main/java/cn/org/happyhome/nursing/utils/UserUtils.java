package cn.org.happyhome.nursing.utils;

import android.content.SharedPreferences;

import cn.org.happyhome.library.base.Const;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.User;

public class UserUtils {
    public static void updateUserInfo(ResultBean<User> resultBean, SharedPreferences sharedPreferences) {
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
    }

}
