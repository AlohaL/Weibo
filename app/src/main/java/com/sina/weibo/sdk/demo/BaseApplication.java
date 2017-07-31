package com.sina.weibo.sdk.demo;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import com.bumptech.glide.request.target.ViewTarget;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.sso.AccessTokenKeeper;

/**
 * Created by a6350 on 2017/4/23.
 */

public class BaseApplication extends Application {

    private static Context context;
    private static Oauth2AccessToken mAccessToken;

    public static Oauth2AccessToken getmAccessToken(){
        return mAccessToken;
    }

    public static void setmAccessToken(Oauth2AccessToken mAccessToken){
        BaseApplication.mAccessToken = mAccessToken;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initData();
        ViewTarget.setTagId(R.id.glide_tag);
    }

    private void initData(){
        mAccessToken = AccessTokenKeeper.readAccessToken(this);
    }

    public static Context getContext(){
        return context;
    }
}