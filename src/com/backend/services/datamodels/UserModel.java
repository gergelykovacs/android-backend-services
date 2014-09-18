package com.backend.services.datamodels;

import com.backend.services.ServiceData;

public class UserModel extends ServiceData {
	
	private int mId;
	private int mUserId;
	private String mEmail;
	private String mUsername;
	private String mPassword;
	private String mSessionToken;
	private long mRegistered;
	private long mUpdated;
	private String mProfilePicture;
	
	public UserModel() {
	}
	
	public UserModel(int mId, int mUserId, String mEmail, String mUsername, String mPassword,
			String mSessionToken, long mRegistered, long mUpdated, String mProfilePicture) {
		this.setId(mId);
		this.setUserId(mUserId);
		this.setEmail(mEmail);
		this.setUsername(mUsername);
		this.setPassword(mPassword);
		this.setSessionToken(mSessionToken);
		this.setRegistered(mRegistered);
		this.setUpdated(mUpdated);
		this.setProfilePicture(mProfilePicture);
	}

	public int getId() {
		return mId;
	}

	public void setId(int mId) {
		this.mId = mId;
	}

	public int getUserId() {
		return mUserId;
	}

	public void setUserId(int mUserId) {
		this.mUserId = mUserId;
	}

	public String getEmail() {
		return mEmail;
	}

	public void setEmail(String mEmail) {
		this.mEmail = mEmail;
	}

	public String getUsername() {
		return mUsername;
	}

	public void setUsername(String mUsername) {
		this.mUsername = mUsername;
	}

	public String getPassword() {
		return mPassword;
	}

	public void setPassword(String mPassword) {
		this.mPassword = mPassword;
	}

	public String getSessionToken() {
		return mSessionToken;
	}

	public void setSessionToken(String mSessionToken) {
		this.mSessionToken = mSessionToken;
	}

	public long getRegistered() {
		return mRegistered;
	}

	public void setRegistered(long mRegistered) {
		this.mRegistered = mRegistered;
	}
	
	public long getUpdated() {
		return mUpdated;
	}

	public void setUpdated(long mUpdated) {
		this.mUpdated = mUpdated;
	}

	public String getProfilePicture() {
		return mProfilePicture;
	}

	public void setProfilePicture(String mProfilePicture) {
		this.mProfilePicture = mProfilePicture;
	}
}
