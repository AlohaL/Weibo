package com.sina.weibo.sdk.demo.bean;

import java.util.List;

/**
 * Created by Steve on 2017/3/30.
 */

public class FeedListBean {

    private String created_at;
    private String idstr;
    private String text;
    private String textLength;
    private List<PhotoBean> pic_urls;
    private UserInfoBean user;

    private int type;

    public int getType() {
        if (pic_urls.size() > 0)
            return 0;
        return 1;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextLength() {
        return textLength;
    }

    public void setTextLength(String textLength) {
        this.textLength = textLength;
    }

    public List<PhotoBean> getPic_urls() {
        return pic_urls;
    }

    public void setPic_urls(List<PhotoBean> pic_urls) {
        this.pic_urls = pic_urls;
    }

    public UserInfoBean getUser() {
        return user;
    }

    public void setUser(UserInfoBean user) {
        this.user = user;
    }
}
