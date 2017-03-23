package com.example.demo.testgradle.http.base;

import com.example.demo.testgradle.bean.ListViewBean;
import com.example.demo.testgradle.bean.UrlBean;
import com.example.demo.testgradle.http.base.net.RabHttpResponse;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by idea on 2017/3/23.
 */

public interface VideoApi {



    @GET("api/postingManage/findPostingSearch")
    Observable<UrlBean<List<ListViewBean>>> getListDate(@Query("longitude") String longitude, @Query("latitude") String latitude, @Query("postType") String postType,
                                                        @Query("sex") String sex, @Query("type") String type, @Query("pageIndex") String pageIndex, @Query("customerPlace") String customerPlace,
                                                        @Query("eAge") String eAge, @Query("pageItemCount") String pageItemCount, @Query("sAge") String sAge,
                                                        @Query("customerId") String customerId, @Query("token") String token,
                                                        @Query("tokenCustomerId") String tokenCustomerId, @Query("job") String job, @Query("chatTheme") String chatTheme);
 @GET("api/postingManage/findPostingSearch")
    Observable<RabHttpResponse<List<ListViewBean>>> getListDate(@QueryMap Map<String, String> map);

}
