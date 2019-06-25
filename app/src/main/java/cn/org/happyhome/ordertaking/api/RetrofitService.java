package cn.org.happyhome.ordertaking.api;


import java.util.List;

import cn.org.happyhome.nursing.bean.AddAddressBean;
import cn.org.happyhome.ordertaking.bean.Address;
import cn.org.happyhome.ordertaking.bean.OrgBean;
import cn.org.happyhome.ordertaking.bean.ResultBean;
import cn.org.happyhome.ordertaking.bean.User;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {

    /**
     * 登陆
     *
     * @param user
     * @return
     */
    @POST("App/login")
    Observable<ResultBean<User>> login(@Body User user);

    /**
     * 根据机构码注册
     *
     * @param user
     * @param organizationID
     * @return
     */
    @POST("App/addUser/0/{organizationID}")
    Observable<ResultBean<User>> register(@Body User user, @Path("organizationID") String organizationID);


    /**
     * 更新用户信息
     *
     * @param token
     * @param user
     * @return
     */
    @POST("App/add/{token}")
    Observable<ResultBean> updateUserInfo(@Path("token") String token, @Body User user);


    /**
     * 拉去地址
     *
     * @param id
     * @return
     */
    @GET("App/FindAll/{id}")
    Observable<ResultBean<List<Address>>> getAddressList(@Path("id") String id);

    @GET("App/DeleteAddress/{userType}/{userId}/{addressId}")
    Observable<ResultBean> deleteAddress(@Path("userType") String userType, @Path("userId") String id, @Path("addressId") String addressId);

    /**
     * 强制更新密码
     *
     * @param user
     * @return
     */
    @POST("App/passwordEdit")
    Observable<ResultBean> updatePassword(@Body User user);

    /**
     * 查询机构
     *
     * @return
     */
    @GET("App/findAllOrg")
    Observable<ResultBean<List<OrgBean>>> findAllOrg();


    /**
     * 绑定地址
     *
     * @param userType
     * @param id
     * @return
     */
    @POST("App/userAdd/{userType}/{userID}")
    Observable<ResultBean> bindAddress(@Path("userType") String userType, @Path("userID") String id, @Body Address address);

}
