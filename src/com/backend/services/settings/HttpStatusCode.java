package com.backend.services.settings;

public enum HttpStatusCode {
	
	HTTP_STATUS_OK(200, false, "-"),
	HTTP_STATUS_NOT_FOUND(404, false, "-"),
	HTTP_STATUS_INTERNAL_SERVER_ERROR(500, false, "-");
	
	private int mInt;
	private boolean mBoolean;
	private String mString;
	
	private HttpStatusCode(int i, boolean b, String s) {
		mInt = i;
		mBoolean = b;
		mString = s;
	}

	public int intValue() {
		return mInt;
	}
	
	public boolean booleanValue() {
		return mBoolean;
	}
	
	public String stringValue() {
		return mString;
	}
}
