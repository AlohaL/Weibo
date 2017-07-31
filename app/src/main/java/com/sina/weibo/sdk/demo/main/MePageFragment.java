package com.sina.weibo.sdk.demo.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.demo.PersonalCenter;
import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.WBAuthActivity;
import com.sina.weibo.sdk.demo.WBDemoMainActivity;
import com.sina.weibo.sdk.demo.utils.GlideCircleTransform;
import com.sina.weibo.sdk.demo.view.LineItem;

/**
 * Created by Steve on 2017/4/15.
 */

public class MePageFragment extends Fragment {

    private ImageView portrait;
    private LineItem mMyWeiboItem;
    private LineItem mMyTopicItem;
    private LineItem mMyCollectionItem;
    private LineItem mMyTokenItem;

    protected Context mContext = null;

    public static MePageFragment newInstance() {
        MePageFragment fragment = new MePageFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.personal_center, container, false);

        portrait = (ImageView) layout.findViewById(R.id.portrait);

        Glide.with(mContext)
                .load(R.drawable.personal_portrait)
                .transform(new GlideCircleTransform(mContext))
                .into(portrait);

        mMyWeiboItem = (LineItem) layout.findViewById(R.id.personal_item_myweibo);
        mMyTopicItem = (LineItem) layout.findViewById(R.id.personal_item_mytopic);
        mMyCollectionItem = (LineItem) layout.findViewById(R.id.personal_item_mycollection);
        mMyTokenItem = (LineItem) layout.findViewById(R.id.personal_item_mytoken);

        mMyWeiboItem.setMidContent("我的微博");
        mMyWeiboItem.setLineShown(true);
        mMyWeiboItem.setRightIV(R.drawable.ic_personal_more);

        mMyTopicItem.setMidContent("我的主页");
        mMyTopicItem.setLineShown(false);
        mMyTopicItem.setRightIV(R.drawable.ic_personal_more);

        mMyCollectionItem.setMidContent("收藏");
        mMyCollectionItem.setLineShown(true);
        mMyCollectionItem.setRightIV(R.drawable.ic_personal_more);

        mMyTokenItem.setMidContent("微博授权");
        mMyTokenItem.setLineShown(true);
        mMyTokenItem.setRightIV(R.drawable.ic_personal_more);

        mMyTokenItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WBAuthActivity.class));
            }
        });

        return layout;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO
        //填充view的内容
        new PersonalCenter();
    }
}
