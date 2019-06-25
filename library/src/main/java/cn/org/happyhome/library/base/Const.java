package cn.org.happyhome.library.base;

public class Const {
    //   用户信息文件名字
    public static final String USER_INFO = "USER_INFO";
    public static final String IS_FIRST = "IS_FIRST";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_TYPE = "USER_TYPE";
    public static final String USER_PHONE = "USER_PHONE";
    public static final String USER_ID = "USER_ID";
    public static final String USER_PASSWORD = "USER_PASSWORD";
    public static final String USER_IMG = "USER_IMG";
    public static final String USER_LEVEL = "USER_LEVEL";
    public static final String PROVINCE = "PROVINCE";
    public static final String CITY = "CITY";
    public static final String DISTRICT = "DISTRICT";
    //机构码
    public static final String ORGANIZATIONID = "ORGANIZATIONID";
    /**
     * 实名认证得状态码
     * 9987 通过审核
     * 9983 审核中
     * 9982 未申请实名认证
     * 9981 未通过审核
     */
    public static final String REAL_NAME_STATE = "REAL_NAME_STATE";
    public static final String REAL_NAME_STATE_9987 = "9987";
    public static final String REAL_NAME_STATE_9983 = "9983";
    public static final String REAL_NAME_STATE_9982 = "9982";
    public static final String REAL_NAME_STATE_9981 = "9981";


    /*订单状态
     * 1  1对2
     * 2  2对3
     * 3  1对3
     * 4  1对1
     * */

    public static final String ORDER_TYPE_1 = "1";
    public static final String ORDER_TYPE_2 = "2";
    public static final String ORDER_TYPE_3 = "3";
    public static final String ORDER_TYPE_4 = "4";


    //七牛上传token
    public static final String QN_TOKEN = "TOKEN";
    //    1是登陆在线状态 0 是退出状态
    public static final String USER_ONLINE = "USER_ONLINE";
    //    用户状态  0是默认状态   1 请假
    public static final String USER_LEAVE = "USER_LEAVE";
    //    请假
    public static final String USER_IS_LEAVE = "1";
    //    未请假
    public static final String USER_NOT_LEAVE = "0";
    //    1 是默认值 1对1  状态  1 是多对多
    public static final String USER_ONE_TO_ONE = "USER_ONE_TO_ONE";
    public static final String USER_MANY_TO_MANY = "USER_MANY_TO_MANY";
    //    1 对 1
    public static final String USER_IS_ONE = "1";
    public static final String USER_NOT_ONE = "0";
    //    多对多
    public static final String USER_IS_MANY = "1";
    public static final String USER_NOT_MANY = "0";


    public static final String APP_ID = "APP_ID";
//    public static final String BASE_USRL = "http://192.168.0.109:8810/";
    public static final String BASE_USRL = "http://www.happyhome.org.cn/app/";
    //    机关推送ID

    public static final String REGISTER_ID = "REGISTER_ID";
    //    是否已经设置过别名
    public static final String HAD_REGISTER = "HAD_REGISTER";

    //    1 代表在线  0 代表下线
    public static final String ONLINE = "1";
    public static final String OFFLINE = "0";

    //   维度
    public static final String LATITUDE = "LATITUDE";
    //    经度
    public static final String LONGITUDE = "LONGITUDE";
    //    地区编码
    public static final String ADCODE = "ADCODE";
    //   护工 已存在绑定地址数量
    public static final String NURS_HAD_ADDRESS_NUMBER = "NURS_HAD_ADDRESS_NUMBER";


    //    强制更新密码失败
    public static final int REAL_NAME_STATE_9980 = 9980;
    //    访问数据正常
    public static final int RESULT_OK = 0;
    //   服务器跨服务调用失败
    public static final int RESULT_9999 = 9999;
    //    申诉重复提交
    public static final int RESULT_9985 = 9985;
    //    重复注册
    public static final int RESULT_HAD_REGISTER = 201;
    //    服务器报错
    public static final int RESULT_ERR = -1;
    //    参数错误
    public static final int RESULT_PARAMETER_ERR = 1;
    //    参数为空
    public static final int RESULT_PARAMETER_NULL = 41000;

    public static final int RESULT_NODATA = 41000;

    // 绑定地址成功的广播频道
    public static final String BIND_ADDRESS_OK = "BIND_ADDRESS_OK";

    //    实名认证状态刷新得广播
    public static final String UPDATE_REAL_NAME_STATE = "UPDATE_REAL_NAME_STATE";
    //    订单被接取时更新订单列表
    public static final String UPDATE_ORDER_STATE = "UPDATE_ORDER_STATE";


    public static final String INTENT_KEY = "INTENT_KEY";
    public static final String INTENT_NAME = "INTENT_NAME";
    public static final String INTENT_EMAIL = "INTENT_EMAIL";
    public static final String INTENT_LATITUDE = "INTENT_LATITUDE";
    public static final String INTENT_LONGTITUDE = "INTENT_LONGTITUDE";
}
