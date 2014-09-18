package com.backend.services.datamodels;

import com.backend.services.ServiceData;

public class QuestionModel extends ServiceData {
	
	private int mId;
	private String mTitle;
	private String mDescription;
	private String mType;
	private long mUpdated;
	private String mPicture;
	
	private int mQuestionId;
	
	public QuestionModel() {
	}

	public QuestionModel(int mId, String mTitle, String mDescription, String mType, 
			long mUpdated, String mPicture, int mQuestionId) {
		
		this.setId(mId);
		this.setTitle(mTitle);
		this.setDescription(mDescription);
		this.setType(mType);
		this.setUpdated(mUpdated);
		this.setPicture(mPicture);
		
		this.setQuestionId(mQuestionId);
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

	public int getQuestionId() {
		return mQuestionId;
	}

	public void setQuestionId(int mQuestionId) {
		this.mQuestionId = mQuestionId;
	}
}
