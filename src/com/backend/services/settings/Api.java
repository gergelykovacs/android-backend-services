package com.backend.services.settings;

public enum Api {
	
	API_HOST(0, false, "https://api.example.tld"),
	
	API_CALL_PARAM_PATTERN(0, false, "%param%"),
	
	API_CALL_USER_LOGIN(0, false, "/user/login"),
	API_CALL_USER_GET(0, false, "/user/%param%"),
	API_CALL_USER_REGISTRATION(0, false, "/user/registration"),
	API_CALL_USER_DELETE(0, false, "/user/%param%/delete"),
	API_CALL_USER_UPDATE(0, false, "/user/%param%/update"),
	
	API_CALL_QUESTION_GET(0, false, "/questions"),
	API_CALL_QUESTION_ADD(0, false, "/question/add"),
	API_CALL_QUESTION_DELETE(0, false, "/question/%params%/delete"),
	
	API_CALL_ANSWER_GET(0, false, "/answers"),
	API_CALL_ANSWER_ADD(0, false, "/answer/add"),
	API_CALL_ANSWER_DELETE(0, false, "/answer/%params%/delete"),
	
	API_FLAG_ERROR(0, false, "error"),
	API_FLAG_ERROR_CODE(0, false, "code"),
	API_FLAG_CONTENT(0, false, "content");
	
	private int mInt;
	private boolean mBoolean;
	private String mString;
	
	private Api(int i, boolean b, String s) {
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
