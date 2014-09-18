package com.backend.services.events;

/**
 * Service Events
 * 
 * <p>Service events are used by the framework to be triggered or 
 * dispatched/captured. These events are the bases of the event 
 * driven framework.</p>
 */
public enum ServiceEvent {
	
	// Do not handle or catch NULL event. 
	EVENT_NULL(0, false, "EVENT_NULL"),
	
	EVENT_GET_USER(0, false, "EVENT_GET_USER"),
	EVENT_REGISTER_USER(0, false, "EVENT_REGISTER_USER"),
	EVENT_UPDATE_USER(0, false, "EVENT_UPDATE_USER"),
	EVENT_DELETE_USER(0, false, "EVENT_DELETE_USER"),
	EVENT_SIGN_IN(0, false, "EVENT_SIGN_IN"),
	EVENT_SIGN_OUT(0, false, "EVENT_SIGN_OUT"),
	
	EVENT_GET_QUESTIONS(0, false, "EVENT_GET_QUESTIONS"),
	EVENT_ADD_QUESTION(0, false, "EVENT_FINISH_QUESTION"),
	EVENT_DELETE_QUESTION(0, false, "EVENT_DELETE_QUESTION"),
	
	EVENT_GET_ANSWERS(0, false, "EVENT_GET_ANSWERS"),
	EVENT_ADD_ANSWER(0, false, "EVENT_FINISH_ANSWER"),
	EVENT_DELETE_ANSWER(0, false, "EVENT_DELETE_ANSWER"),
	
	EVENT_ERROR_NETWORK_DOWN(0, false, "EVENT_ERROR_NETWORK_DOWN"),
	
	EVENT_ERROR_IN_COMMUNICATION(0, false, "EVENT_ERROR_IN_COMMUNICATION"),
	
	EVENT_ERROR_USER_NOT_FOUND(0, false, "EVENT_ERROR_USER_NOT_FOUND"),
	EVENT_ERROR_QUESTION_NOT_FOUND(0, false, "EVENT_ERROR_QUESTION_NOT_FOUND"),
	EVENT_ERROR_ANSWER_NOT_FOUND(0, false, "EVENT_ERROR_ANSWER_NOT_FOUND"),
	
	EVENT_ERROR_SYSTEM(0, false, "EVENT_ERROR_SYSTEM"),
	EVENT_ERROR_UNKNOWN(0, false, "EVENT_ERROR_UNKNOWN");
	
	private int mInt;
	private boolean mBoolean;
	private String mString;
	
	private ServiceEvent(int i, boolean b, String s) {
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
