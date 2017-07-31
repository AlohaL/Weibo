package com.sina.weibo.sdk.demo.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


public class ScalableImageViewActivity extends Activity {

    public static final String KEY_IMAGE_URL = "KEY_IMAGE_URL";

    public static final String KEY_MULTI_IMAGE_URL = "KEY_MULTI_IMAGE_URL";
    public static final String KEY_MULTI_IMAGE = "KEY_MULTI_IMAGE";
    public static final String KEY_IMAGE_COUNT = "KEY_IMAGE_COUNT";
    public static final String KEY_CURRENT_POSITION = "KEY_CURRENT_POSITION";

    private final int SCALABLE_IMAGE_INFO_TV_DURATION = 2000;
    private ViewPager scalable_image_view_pager;
    private TextView tv_indicator;
    private ScalableImageViewPagerAdapter scalable_image_view_pager_adapter;
    private List<SliderViewPagerItem> items;

    private Handler handler;

    // variables for multiple image case
    private boolean isMultipleImage = false;
    private int mCurrentPos;
    private int mImageCount;
    private String[] mImageUrlList;

    private int default_image_max_width = -1;
    private int default_image_max_height = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_scalable_image_view);
        findViews();
        initVars();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void findViews() {

        this.scalable_image_view_pager = (ViewPager) findViewById(R.id.scalable_image_view_pager);
        tv_indicator = (TextView) findViewById(R.id.tv_indicator);
    }

    private void initVars() {
        Intent intent = getIntent();
        this.items = new ArrayList<SliderViewPagerItem>();
        this.handler = new Handler();

        this.isMultipleImage = intent.getBooleanExtra(KEY_MULTI_IMAGE, false);

        boolean isImageDescExist = true;
        if (this.isMultipleImage) {
            isImageDescExist = handleMultipleImage(intent);
        } else {
            isImageDescExist = handleSingleImage(intent);
        }

//       calcuDefaultImageArea(isImageDescExist);

        this.scalable_image_view_pager_adapter = new ScalableImageViewPagerAdapter(ScalableImageViewActivity.this, items);
        this.scalable_image_view_pager.setAdapter(this.scalable_image_view_pager_adapter);
        this.scalable_image_view_pager.setOnPageChangeListener(new MyOnPageChangeListener());

        if (this.isMultipleImage) {
            this.scalable_image_view_pager.setCurrentItem(this.mCurrentPos);
        } else {
            this.scalable_image_view_pager.setCurrentItem(0);
        }
    }

    private void calcuDefaultImageArea(boolean isImageDescExist) {
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);

        if (isImageDescExist) {
            default_image_max_width = point.x - (int) ScreenUtils.dpToPx(this, 10);
            default_image_max_height = point.y - (int) ScreenUtils.dpToPx(this, 158);
        } else {
            default_image_max_width = point.x - (int) ScreenUtils.dpToPx(this, 10);
            default_image_max_height = point.y - (int) ScreenUtils.dpToPx(this, 60);
        }
    }

    private boolean handleSingleImage(Intent intent) {
        String imageUrl = intent.getStringExtra(KEY_IMAGE_URL);

        SliderViewPagerItem singleItem = new SliderViewPagerItem();
        singleItem.setImageUrl(imageUrl);
        this.items.add(singleItem);

        return false;
    }

    private boolean handleMultipleImage(Intent intent) {
        // load data
        this.mImageCount = intent.getIntExtra(KEY_IMAGE_COUNT, 0);
        this.mCurrentPos = intent.getIntExtra(KEY_CURRENT_POSITION, 0);
        this.mImageUrlList = intent.getStringArrayExtra(KEY_MULTI_IMAGE_URL);

        for (String imageUrl : mImageUrlList) {
            SliderViewPagerItem item = new SliderViewPagerItem();
            item.setAssociatedUrl("").setImageUrl(imageUrl).setDescription("").setTitle("");
            this.items.add(item);
        }

        return false;
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        public  MyOnPageChangeListener(){
            tv_indicator.setText("" +   1  + "/" + mImageUrlList.length);
        }
        @Override
        public void onPageSelected(int position) {

            tv_indicator.setVisibility(View.VISIBLE);
            tv_indicator.setText("" + (position + 1) + "/" + mImageUrlList.length);


        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//			if (isMultipleImage && mImageCount > 1 && positionOffset == 0) {
//				if (position == 0) {
//					scalable_image_view_pager_adapter.resetImageState(position + 1);
//				} else if (position == items.size() - 1) {
//					scalable_image_view_pager_adapter.resetImageState(position - 1);
//				} else {
//					scalable_image_view_pager_adapter.resetImageState(position + 1);
//					scalable_image_view_pager_adapter.resetImageState(position - 1);
//				}
//			}
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}

