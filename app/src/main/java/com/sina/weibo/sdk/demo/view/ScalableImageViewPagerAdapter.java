package com.sina.weibo.sdk.demo.view;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.BaseApplication;

import java.util.List;

public class ScalableImageViewPagerAdapter extends PagerAdapter {

    private Activity activity;
    private List<SliderViewPagerItem> images;
    private View[] viewList;

    public ScalableImageViewPagerAdapter(Activity activity, List<SliderViewPagerItem> items) {
        this.activity = activity;
        this.images = items;
        this.viewList = new View[items.size()];
    }


    @Override
    public View instantiateItem(ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.scalable_image_view_pager_item, container, false);
        initTarget(view, position);
        container.addView(view);
        viewList[position] = view;
        return view;
    }

    private void initTarget(View view, int position) {

        final ScalableImageTarget scalableImageTarget = (ScalableImageTarget) view.findViewById(R.id.scalable_image_view_pager_item);
        scalableImageTarget.setSingleTapListener(singleTapListener);
        String path = this.images.get(position).getImageUrl();
//        PhotoUtil.ImageSize mImageSize= PhotoUtil.getImageSize(path);
        Glide.with(BaseApplication.getContext())
                .load(path)
                .into(scalableImageTarget);
    }

    @Override
    public int getCount() {
        return this.images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }



    private ScalableImageView.SingleTapListener singleTapListener = new ScalableImageView.SingleTapListener() {

        @Override
        public void singleTapped() {
            activity.finish();
        }
    };


}
