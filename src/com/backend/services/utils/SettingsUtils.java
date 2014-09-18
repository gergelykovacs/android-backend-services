package com.backend.services.utils;

import com.backend.services.settings.HttpStatusCode;

public class SettingsUtils {
	
	private SettingsUtils() {
	}
	
	public static HttpStatusCode findHttpStatusCodeBy(int code) {
		for(HttpStatusCode c : HttpStatusCode.values()) {
			if(c.intValue() == code) {
				return c;
			}
		}
		return null;
	}

	@Override
	public final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
