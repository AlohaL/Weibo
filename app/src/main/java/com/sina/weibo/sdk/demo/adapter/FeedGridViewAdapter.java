package com.sina.weibo.sdk.demo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.view.ScalableImageViewActivity;

import java.util.ArrayList;
import java.util.List;

public class FeedGridViewAdapter extends ArrayAdapter<String> {
    private final String TAG = FeedGridViewAdapter.class.getSimpleName();

    private Context mContext;
    private int mLayoutResourceId;
    private List<String> picItems = new ArrayList<String>();

    public FeedGridViewAdapter(Context context, int resource, List<String> data) {
        super(context, resource, data);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.picItems = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayoutResourceId, parent, false);
            holder = new RecordHolder();
     //       holder.image = (ImageView) convertView.findViewById(R.id.feed_pic_item);
            holder.image.setTag(position);
            holder.image.setOnClickListener(getImageOnClickedListener());
            convertView.setTag(holder);
        } else {
            holder = (RecordHolder) convertView.getTag();
        }
        Glide.with(mContext).load(picItems.get(position))
                .centerCrop().placeholder(R.drawable.ic_logo)
                .error(R.drawable.ic_logo)
                .into(holder.image);
        return convertView;
    }

    private View.OnClickListener getImageOnClickedListener() {
        View.OnClickListener listener = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int tag = ((Integer) v.getTag()).intValue();
                String[] imageUrls = new String[picItems.size()];
                for (int i = 0; i < picItems.size(); i++) {
                    imageUrls[i] = picItems.get(i);
                }
                Intent intent = new Intent(mContext, ScalableImageViewActivity.class);
                intent.putExtra(ScalableImageViewActivity.KEY_MULTI_IMAGE, true);
                intent.putExtra(ScalableImageViewActivity.KEY_IMAGE_COUNT, picItems.size());
                intent.putExtra(ScalableImageViewActivity.KEY_CURRENT_POSITION, tag);
                intent.putExtra(ScalableImageViewActivity.KEY_MULTI_IMAGE_URL, imageUrls);
                mContext.startActivity(intent);
            }
        };

        return listener;
    }

    private class RecordHolder {
        private ImageView image;
    }
}
