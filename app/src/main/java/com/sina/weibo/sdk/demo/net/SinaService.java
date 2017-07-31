package com.sina.weibo.sdk.demo.net;

import com.sina.weibo.sdk.demo.bean.FeedList;
import com.sina.weibo.sdk.demo.bean.UserInfoBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Steve on 2017/5/12.
 */

public interface SinaService {
    /**
     * 获取用户的基本信息
     */
    @GET("users/show.json")
    Call<UserInfoBean> getUserInfo(@Query("access_token") String token, @Query("uid") String uid);

    /**
     * 获取个人微博
     */
    @GET("statuses/home_timeline.json")
    Call<FeedList> getTimeline(@Query("access_token") String token, @Query("page") int page, @Query("count") int count);
}
