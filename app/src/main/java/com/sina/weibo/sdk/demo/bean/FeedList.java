package com.sina.weibo.sdk.demo.bean;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 2017/4/6.
 */

public class FeedList {

    List<FeedListBean> statuses = new ArrayList<>();

    public List<FeedListBean> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<FeedListBean> statuses) {
        this.statuses = statuses;
    }
}
