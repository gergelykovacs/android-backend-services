package com.backend.services.utils;

public class CommonUtils {
	
	private CommonUtils() {
	}
	
	/**
	 * Replace, paste arguments one after the other into 
	 * the base at positions specified by a RegEx string.
	 * 
	 * @param base The input string.
	 * @param regex The pattern for search.
	 * @param args The replacements.
	 * @return The modified original string.
	 */
	public static String paste(String base, String regex, String... args) {
		for(String arg : args) {
			base = base.replaceFirst(regex, arg);
		}
		return base;
	}
	
	/**
	 * String Holder
	 * 
	 * <p>Variable as String can not be passed by reference. 
	 * To circumvent this problem a holder or wrapper class is 
	 * defined.</p>
	 */
	public static class StringHolder {
		
		private String mString;
		
		public StringHolder() {
		}
		
		public StringHolder(String mString) {
			this.setString(mString);
		}

		public String getString() {
			return mString;
		}

		public void setString(String mString) {
			this.mString = mString;
		}
	}
	
	@Override
	public final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
