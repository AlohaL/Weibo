package com.sina.weibo.sdk.demo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.LayoutInflater;

import com.sina.weibo.sdk.demo.R;

/**
 * Created by a6350 on 2017/4/15.
 */

public class LineItem extends RelativeLayout {

    private Context mContext;
    private LayoutInflater mInflater;
    private TextView mMidContent;
    private ImageView mRightIV;
    private Boolean lineShown = true;
    private ImageView mLineImageView;
    private RelativeLayout layout;

    public LineItem(Context context){
        this(context, null);
    }
    public LineItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineItem(Context context,AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);

        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.layout = (RelativeLayout) this.mInflater.inflate(R.layout.item_line,this,true);
        mRightIV = (ImageView) this.layout.findViewById(R.id.item_line_more);
        mLineImageView = (ImageView) this.layout.findViewById(R.id.item_line_separator) ;
        mMidContent = (TextView) this.layout.findViewById(R.id.item_line_text);


        TypedArray array = context.obtainStyledAttributes( attrs, R.styleable.LineItem);

        mMidContent.setText(array.getString(R.styleable.LineItem_midcontent));
        lineShown=array.getBoolean(R.styleable.LineItem_lineShown,true);
        mRightIV.setImageResource(array.getResourceId(R.styleable.LineItem_rightimage,R.drawable.ic_personal_more));
        if(!lineShown)
            mLineImageView.setVisibility(View.GONE);

        array.recycle();
    }

    public void setMidContent(String mMidContent){
        this.mMidContent.setText(mMidContent);
    }

    public void setLineShown(Boolean visible){
        if(visible){
            mLineImageView.setVisibility(View.VISIBLE);
        }else{
            mLineImageView.setVisibility(View.GONE);
        }

    }

    public void setRightIV(int id){
        this.mRightIV.setImageResource(id);
    }


}
