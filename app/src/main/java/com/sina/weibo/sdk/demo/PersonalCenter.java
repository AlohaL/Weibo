package com.sina.weibo.sdk.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.demo.view.LineItem;
import com.sina.weibo.sdk.demo.utils.GlideCircleTransform;

/**
 * Created by a6350 on 2017/4/9.
 */

public class PersonalCenter extends Activity {
    private ImageView portrait;
    private LineItem mMyWeiboItem;
    private LineItem mMyTopicItem;
    private LineItem mMyCollectionItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_center);
        portrait = (ImageView) findViewById(R.id.portrait);

        Glide.with(this)
                .load(R.drawable.personal_portrait)
                .transform(new GlideCircleTransform(this))
                .into(portrait);

        mMyWeiboItem = (LineItem) findViewById(R.id.personal_item_myweibo);
        mMyTopicItem = (LineItem) findViewById(R.id.personal_item_mytopic);
        mMyCollectionItem = (LineItem) findViewById(R.id.personal_item_mycollection);

        mMyWeiboItem.setMidContent("我的微博");
        mMyWeiboItem.setLineShown(true);
        mMyWeiboItem.setRightIV(R.drawable.ic_personal_more);

        mMyTopicItem.setMidContent("我的主页");
        mMyTopicItem.setLineShown(false);
        mMyTopicItem.setRightIV(R.drawable.ic_personal_more);

        mMyCollectionItem.setMidContent("收藏");
        mMyCollectionItem.setLineShown(true);
        mMyCollectionItem.setRightIV(R.drawable.ic_personal_more);
    }



}

