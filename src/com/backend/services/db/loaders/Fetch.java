package com.backend.services.db.loaders;

/**
 * Fetch
 * 
 * <p>It is a customization parameter that may be used to 
 * implement and distinguish different behaviors of single fetch 
 * logic. 
 * The interface defined function given for a single fetch would not be reusable 
 * with its parameters in different situations. Hence a fetch semantic is introduced 
 * by this enum.</p>
 * 
 * @see com.backend.services.db.loaders.ILoader
 */
public enum Fetch {

	BY_ID,
	BY_USER_ID,
	BY_USERNAME_PASSWORD,
	BY_START_DATE,
	BY_END_DATE
}
