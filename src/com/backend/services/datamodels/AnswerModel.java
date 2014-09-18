package com.backend.services.datamodels;

import com.backend.services.ServiceData;

public class AnswerModel extends ServiceData {
	
	private int mId;
	private String mTitle;
	private String mDescription;
	private String mType;
	private long mUpdated;
	private String mPicture;
	
	private int mAnswerId;
	private String mContent;
	private String mState;
	
	public AnswerModel() {
	}

	public AnswerModel(int mId, String mTitle, String mDescription, String mType, 
			long mUpdated, String mPicture, 
			int mAnswerId, String mContent, String mState) {
		
		this.setId(mId);
		this.setTitle(mTitle);
		this.setDescription(mDescription);
		this.setType(mType);
		this.setUpdated(mUpdated);
		this.setPicture(mPicture);
		
		this.setAnswerId(mAnswerId);
		this.setContent(mContent);
		this.setState(mState);
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String mTitle) {
		this.mTitle = mTitle;
	}

	public String getDescription() {
		return mDescription;
	}

	public void setDescription(String mDescription) {
		this.mDescription = mDescription;
	}

	public String getType() {
		return mType;
	}

	public void setType(String mType) {
		this.mType = mType;
	}
	
	public long getUpdated() {
		return mUpdated;
	}

	public void setUpdated(long mUpdated) {
		this.mUpdated = mUpdated;
	}
	

	public String getPicture() {
		return mPicture;
	}

	public void setPicture(String mPicture) {
		this.mPicture = mPicture;
	}

	public String getState() {
		return mState;
	}

	public void setState(String mState) {
		this.mState = mState;
	}

	public int getAnswerId() {
		return mAnswerId;
	}

	public void setAnswerId(int mAnswerId) {
		this.mAnswerId = mAnswerId;
	}

	public String getContent() {
		return mContent;
	}

	public void setContent(String mContent) {
		this.mContent = mContent;
	}
}
