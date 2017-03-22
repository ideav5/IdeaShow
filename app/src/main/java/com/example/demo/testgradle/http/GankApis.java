package com.example.demo.testgradle.http;


import com.example.demo.testgradle.bean.ListViewBean;
import com.example.demo.testgradle.bean.UrlBean;
import com.example.demo.testgradle.bean.UserBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by codeest on 16/8/19.
 */

public interface GankApis {

    //    String HOST = "https://app.rablive.cn/";
        String HOST = "http://apptest.rablive.cn:8080/";
//    String HOST = "http://192.168.1.72:8080/folkcam-rablive-app/";

    //    ?tokenCustomerId=1129&pageItemCount=20&customerId=1129&token=71378B5F2FC3142BFC4F5F417A61CC31BB0C3F63&pageIndex=1
    //{tokenCustomerId}/{pageItemCount}/{customerId}/{token}/{pageIndex}
    @GET("api/customerChated/getCustomerChated")
    Observable<UrlBean<List<UserBean>>> getList(@Query("tokenCustomerId") String tokenCustomerId, @Query("pageItemCount") String pageItemCount,
                                                @Query("customerId") String customerId, @Query("token") String token, @Query("pageIndex") String pageIndex);



    @GET("api/postingManage/findPostingSearch")
    Observable<UrlBean<List<ListViewBean>>> getListDate(@Query("longitude") String longitude, @Query("latitude") String latitude, @Query("postType") String postType,
                                                        @Query("sex") String sex, @Query("type") String type, @Query("pageIndex") String pageIndex, @Query("customerPlace") String customerPlace,
                                                        @Query("eAge") String eAge, @Query("pageItemCount") String pageItemCount, @Query("sAge") String sAge,
                                                        @Query("customerId") String customerId, @Query("token") String token,
                                                        @Query("tokenCustomerId") String tokenCustomerId, @Query("job") String job, @Query("chatTheme") String chatTheme);


    @FormUrlEncoded
    @POST("api/customer/initCustomerPosition")
    Observable<UrlBean<List<UserBean>>> postList(@Field("e") String e, @Field("longitude") String longitude, @Field("latitude") String latitude,
                                                 @Field("customerId") String customerId, @Field("token") String token, @Field("positionName") String positionName, @Field("tokenCustomerId") String tokenCustomerId);
}

