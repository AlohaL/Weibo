package com.sina.weibo.sdk.demo.net;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Steve on 2017/5/12.
 */

public class SinaRetrofitUtil {

    private static SinaService service;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("https://api.weibo.com/2/")
                            //负责发起Http请求的客户端，这边用OkHttp
                            .client(new OkHttpClient.Builder().build())
                            //解析JSON，这边用Gson
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

        service = retrofit.create(SinaService.class);
    }

    public static SinaService getService(){
            return service;
    }

}
