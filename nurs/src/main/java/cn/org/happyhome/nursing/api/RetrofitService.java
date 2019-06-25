package cn.org.happyhome.nursing.api;


import java.util.List;
import java.util.Map;

import cn.org.happyhome.nursing.bean.AddAddressBean;
import cn.org.happyhome.nursing.bean.Address;
import cn.org.happyhome.nursing.bean.AppealBean;
import cn.org.happyhome.nursing.bean.NewsInfomationBean;
import cn.org.happyhome.nursing.bean.OrderContentBean;
import cn.org.happyhome.nursing.bean.ResultBean;
import cn.org.happyhome.nursing.bean.ServiceContentBean;
import cn.org.happyhome.nursing.bean.ServiceItemBean;
import cn.org.happyhome.nursing.bean.ServiceTypeBean;
import cn.org.happyhome.nursing.bean.TuserSumdetails;
import cn.org.happyhome.nursing.bean.User;
import cn.org.happyhome.nursing.bean.VOrderMm;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RetrofitService {


    /**
     * 更新用户信息
     *
     * @param token
     * @param user
     * @return
     */
    @POST("App/addUser/{token}/{organizationID}")
    Observable<ResultBean<User>> updateUserInfo(@Path("token") String token, @Body User user, @Path("organizationID") String organizationID);


    /**
     * @param id
     * @return 获取已绑定 的地址
     */
    @GET("App/FindAll/{id}")
    Observable<ResultBean<List<Address>>> getAddressList(@Path("id") String id);

    /**
     * 将极光推送的 registerid 上传只服务器
     *
     * @param userId
     * @param registerId
     * @return
     */
    @GET("App/AddJPush/{userId}/{registerId}")
    Observable<ResultBean> registerJPUSH(@Path("userId") String userId, @Path("registerId") String registerId);


    /**
     * 删除地址
     *
     * @param userType
     * @param id
     * @param addressId
     * @return
     */
    @GET("App/DeleteAddress/{userType}/{userId}/{addressId}")
    Observable<ResultBean> deleteAddress(@Path("userType") String userType, @Path("userId") String id, @Path("addressId") String addressId);

    /**
     * 获取未绑定的地址
     *
     * @param code
     * @return
     */
    @GET("App/FindByCode/{code}/{userId}")
    Observable<ResultBean<List<AddAddressBean>>> selectAddressByCode(@Path("code") String code, @Path("userId") String userId);

    /**
     * 绑定地址
     *
     * @param userType
     * @param id
     * @param list
     * @return
     */
    @POST("App/bindingAddress/{userType}/{userId}")
    Observable<ResultBean> bindAddress(@Path("userType") String userType, @Path("userId") String id, @Body List<AddAddressBean> list);

    /**
     * 多文件上传
     *
     * @param requestBody
     * @param id
     * @param type
     * @return
     */
    @POST("App/upload/{type}/{id}")
//    @POST("/tuser/batch/upload/{type}/{id}")
    Observable<ResultBean<User>> uploadFile(@Body RequestBody requestBody, @Path("id") String id, @Path("type") String type);


    /**
     * 根据用户ID 和 类型 获取 服务项目类型  日间  夜间 。。。。。
     *
     * @param userType
     * @param userId
     * @return
     */
    @GET("App/FindServiceTypeByUserID/{userType}/{userId}")
    Observable<ResultBean<List<ServiceTypeBean>>> findServiceTypeByUserID(@Path("userType") String userType, @Path("userId") String userId);


    /**
     * 根据用户ID 和 类型  添加用户选择的 服务类型
     *
     * @param userType
     * @param userId
     * @param list
     * @return
     */
    @POST("App/addtuserservicetype/{userType}/{userId}")
    Observable<ResultBean> addServiceTypeByUserID(@Path("userType") String userType, @Path("userId") String userId, @Body List<ServiceTypeBean> list);


    /**
     * 查询多对多的订单
     *
     * @param type
     * @return
     */
    @GET("App/FindAllMOM")
    Observable<ResultBean<List<VOrderMm>>> selectManyToMany(
            @Query("ServiceType") String type,
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );

    /**
     * 查询订单详情
     *
     * @param OrderID
     * @param type    type 1 为接单，2 历史订单查询
     * @return
     */
    @GET("App/FindServiceContentByOrderID")
    Observable<ResultBean<List<OrderContentBean>>> selectOrderContent(
            @Query("OrderID") String OrderID,
            @Query("userId") String userId,
            @Query("type") String type
    );


    /**
     * 护工接单
     *
     * @param list
     * @return
     */
    @POST("App/SaveOrderDetail")
    Observable<ResultBean<String>> takeOrderById(@Body List<ServiceItemBean> list);


    /**
     * 护工根据状态查询订单
     *
     * @return
     * @Param map  [userId , status, page, pageSize]
     */
    @GET("App/findCarerList")
    Observable<ResultBean<List<VOrderMm>>> selectUserOrderByStatus(@QueryMap Map<String, String> map);


    /**
     * 获取积分记录
     *
     * @param userId
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GET("App/findSumdetailsByUserid")
    Observable<ResultBean<List<TuserSumdetails>>> selectSumdetailsByUserid(
            @Query("userId") String userId,
            @Query("currentPage") int currentPage,
            @Query("pageSize") int pageSize
    );


    /**
     * 获取轮播图 及  新闻列表
     *
     * @param type 1 为轮播图  2为新闻列表
     * @return
     */
    @GET("App/downloadAll")
    Observable<ResultBean<List<NewsInfomationBean>>> downloadAll(
            @Query("type") String type
    );


    /**
     * 查询用户信息
     *
     * @param userId
     * @return
     */
    @GET("App/FindByID")
    Observable<ResultBean<User>> getUserInfo(
            @Query("userID") String userId
    );


    /**
     * 申诉订单
     *
     * @return
     */

    @POST("App/appealSave")
    Observable<ResultBean> appealOrder(
            @Body AppealBean appealBean);


    /**
     * 实名认证
     *
     * @param userId
     * @param files
     * @return
     */
    @POST("App/realname/{UserId}/{IDNumber}")
    Observable<ResultBean> realName(
            @Path("UserId") String userId,
            @Path("IDNumber") String carId,
            @Body List<String> files
    );

    /**
     * 获取七牛得下载凭证
     *
     * @return
     */
    @GET("App/getToken")
    Observable<ResultBean<String>> getToken();

    /**
     * 查询是否已经通过实名认证
     *
     * @return
     */
    @GET("App/getAuthFlag")
    Observable<ResultBean<String>> getAuthFlag(
            @Query("userId") String userId
    );

    /**
     * 退出登陆
     *
     * @param user
     * @return
     */
    @POST("App/logout")
    Observable<ResultBean<User>> loginOut(
            @Body User user
    );

    /**
     * 获取 用户服务项目
     *
     * @param id
     * @return
     */
    @GET("App/findServiceContByUserID")
    Observable<ResultBean<List<ServiceContentBean>>> findServiceContentByUserId(
            @Query("userid") String id
    );

    /**
     * 保存用户得服务项目
     *
     * @param list
     * @return
     */
    @POST("App/saveServiceContUserID")
    Observable<ResultBean<String>> saveServiceContUserID(
            @Body List<ServiceContentBean> list
    );


}
