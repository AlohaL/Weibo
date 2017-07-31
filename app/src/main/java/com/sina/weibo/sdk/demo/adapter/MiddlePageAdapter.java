package com.sina.weibo.sdk.demo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.bean.FeedListBean;
import com.sina.weibo.sdk.demo.bean.UserInfoBean;
import com.sina.weibo.sdk.demo.view.NonScrollableGridView;

import java.util.List;

/**
 * Created by a6350 on 2017/5/7.
 */

public class MiddlePageAdapter extends BaseAdapter {
    private List<FeedListBean> mFeedList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MiddlePageAdapter(Context context, List<FeedListBean> feedlist){
        this.mContext=context;
        this.mFeedList=feedlist;
        mLayoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mFeedList.size();
    }

    @Override
    public FeedListBean getItem(int position) {
        return mFeedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FeedListBean bean =mFeedList.get(position);
        ImageViewHolder imageVH;
        TextViewHolder textVH;
        int type = getItemViewType(position);
        if (convertView == null){
            switch (type){
                case 0:
                    imageVH = new ImageViewHolder();
                    convertView = mLayoutInflater.inflate(R.layout.item_img, null);

                    imageVH.feedContentDesc = (TextView) convertView.findViewById(R.id.feed_desc);
                    imageVH.feedCreateTime = (TextView)  convertView.findViewById(R.id.create_time);
                    imageVH.userName = (TextView) convertView.findViewById(R.id.user_name);

                    imageVH.feedContentDesc.setText(bean.getText());
                    imageVH.feedCreateTime.setText(bean.getCreated_at());
    //                imageVH.userName.setText(bean.getUser().getName());

                    convertView.setTag(imageVH);
                    break;
                case 1:
                    textVH = new TextViewHolder();
                    convertView = mLayoutInflater.inflate(R.layout.item_txt, null);

                    textVH.feedContentDesc = (TextView) convertView.findViewById(R.id.feed_desc);
                    textVH.feedCreateTime = (TextView) convertView.findViewById(R.id.create_time);
                    textVH.userName = (TextView) convertView.findViewById(R.id.user_name);

                    textVH.feedContentDesc.setText(bean.getText());
                    textVH.feedCreateTime.setText(bean.getCreated_at());
//                    textVH.userName.setText(bean1.getName());

                    convertView.setTag(textVH);
                    break;
            }
        }else{
            switch (type){
                case 0:
                    imageVH = (ImageViewHolder) convertView.getTag();
                    imageVH.feedContentDesc.setText(bean.getText());
                    imageVH.feedCreateTime.setText(bean.getCreated_at());
//                    imageVH.userName.setText(bean.getUser().getName());
                    break;
                case 1:
                    textVH = (TextViewHolder) convertView.getTag();
                    textVH.feedContentDesc.setText(bean.getText());
                    textVH.feedCreateTime.setText(bean.getCreated_at());
 //                   textVH.userName.setText(bean.getUser().getName());
                    break;
            }
        }
        return convertView;
    }


    /**
     *  根据数据源的position返回需要显示的的layout的type
     *  type的值必须从0开始
     **/
    @Override
    public int getItemViewType(int position) {
        return mFeedList.get(position).getType();
    }

    /**
     * 返回所有的layout的数量
     */
    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    class ImageViewHolder{
        RelativeLayout userInfoWrapper, inter;
        ImageView userAvatar;
        TextView userName;
        TextView feedCreateTime;
        TextView feedTypeDesc;
        TextView feedContentDesc;
        RelativeLayout feedContentWrapper, user_desc_wrapper;
        RelativeLayout microblogPicsWrapper;
        NonScrollableGridView microblogPics;
    }

    class TextViewHolder{
        RelativeLayout userInfoWrapper, inter;
        ImageView userAvatar;
        TextView userName;
        TextView feedCreateTime;
        TextView feedTypeDesc;
        TextView feedContentDesc;
        RelativeLayout feedContentWrapper, user_desc_wrapper;
    }


}
