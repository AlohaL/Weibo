package com.sina.weibo.sdk.demo.view;

import android.os.Parcel;
import android.os.Parcelable;

public class SliderViewPagerItem implements Parcelable {

	private String associatedUrl;
	private String imageUrl;
	
	private String description;
	private String title;
	
	public SliderViewPagerItem() {
		super();
	}
	
	public SliderViewPagerItem(Parcel in) {
		super();
		this.associatedUrl = in.readString();
		this.imageUrl = in.readString();
		// this.imageId = in.readInt();
		this.description = in.readString();
		this.title = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.getAssociatedUrl());
		dest.writeString(this.getImageUrl());
		// dest.writeInt(this.getImageId());
		dest.writeString(this.getDescription());
		dest.writeString(this.getTitle());
	}
	
	public SliderViewPagerItem setAssociatedUrl(String url) {
		this.associatedUrl = url;
		return this;
	}
	
	public SliderViewPagerItem setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}
	
	public SliderViewPagerItem setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public SliderViewPagerItem setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getAssociatedUrl() {
		return this.associatedUrl;
	}
	
	public String getImageUrl() {
		return this.imageUrl;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getTitle() {
		return this.title;
	}
}
