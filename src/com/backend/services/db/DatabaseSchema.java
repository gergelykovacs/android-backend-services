package com.backend.services.db;

/**
 * The Database Schema
 * 
 * <p>The present class describes the entire database structure 
 * with its tables.</p>
 */
public class DatabaseSchema {
	
	public static final String DATABASE_NAME = "backendservice.db";
	public static final int DATABASE_VERSION = 1;
	
	public static final String[] CREATE_ALL = {
		User.CREATE_TABLE, 
		Question.CREATE_TABLE,
		Answer.CREATE_TABLE
	};
	
	public static final String[] DROP_ALL = {
		User.DROP_TABLE,
		Question.DROP_TABLE,
		Answer.DROP_TABLE
	};
	
	public static final String[][] INDEX_ALL = {
		User.INDEX_TABLE,
		Question.INDEX_TABLE,
		Answer.INDEX_TABLE
	};
	
	public static class User {
		
		public static final String TABLE_NAME = "UserTable";
		
		public static final String KEY_ID = "_id";
		public static final String KEY_USER_ID = "UserId";
		public static final String KEY_EMAIL = "Email";
		public static final String KEY_USERNAME = "Username";
		public static final String KEY_PASSWORD = "Password";
		public static final String KEY_SESSION_TOKEN = "SessionToken";
		public static final String KEY_REGISTERED = "Registered";
		public static final String KEY_UPDATED = "Updated";
		public static final String KEY_PROFILE_PICTURE = "ProfilePicture";
		
		public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME
			+"("+
				KEY_ID +" INTEGER PRIMARY KEY, "+
				KEY_USER_ID +" INTEGER, "+
				KEY_EMAIL +" TEXT, "+
				KEY_USERNAME +" TEXT, "+
				KEY_PASSWORD +" TEXT, "+
				KEY_SESSION_TOKEN +" TEXT, "+
				KEY_REGISTERED +" INTEGER, "+
				KEY_UPDATED +" INTEGER, "+
				KEY_PROFILE_PICTURE +" TEXT"+
			");";
		
		public static final String[] INDEX_TABLE = {
			"CREATE INDEX "+ KEY_USER_ID +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_USER_ID +");",
			"CREATE INDEX "+ KEY_EMAIL +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_EMAIL +");",
			"CREATE INDEX "+ KEY_USERNAME +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_USERNAME +");",
			"CREATE INDEX "+ KEY_PASSWORD +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_PASSWORD +");"
		};
		
		public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME +";";
	}
	
	public static class Content {
		
		public static final String KEY_ID = "_id";
		public static final String KEY_TITLE = "Title";
		public static final String KEY_DESCRIPTION = "Description";
		public static final String KEY_TYPE = "Type";
		public static final String KEY_UPDATED = "Updated";
		public static final String KEY_PICTURE = "Picture";
	}
	
	public static class Question extends Content {
		
		public static final String TABLE_NAME = "QuestionTable";
		
		public static final String KEY_QUESTION_ID = "QuestionId";
		
		public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME
			+"("+
				KEY_ID +" INTEGER PRIMARY KEY, "+
				KEY_TITLE +" TEXT, "+
				KEY_DESCRIPTION +" TEXT, "+
				KEY_TYPE +" TEXT, "+
				KEY_UPDATED +" INTEGER, "+
				KEY_PICTURE +" TEXT, "+
				
				KEY_QUESTION_ID +" INTEGER "+
			");";
		
		public static final String[] INDEX_TABLE = {
			"CREATE INDEX "+ KEY_TITLE +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_TITLE +");",
			"CREATE INDEX "+ KEY_TYPE +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_TYPE +");",
			"CREATE INDEX "+ KEY_UPDATED +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_UPDATED +");",
			
			"CREATE INDEX "+ KEY_QUESTION_ID +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_QUESTION_ID +");"
		};
		
		public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME +";";
	}
	
	public static class Answer extends Content {
		
		public static final String TABLE_NAME = "AnswerTable";
		
		public static final String KEY_ANSWER_ID = "AnswerId";
		public static final String KEY_CONTENT = "Content";
		public static final String KEY_STATE = "State";
		
		public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME
			+"("+
				KEY_ID +" INTEGER PRIMARY KEY, "+
				KEY_TITLE +" TEXT, "+
				KEY_DESCRIPTION +" TEXT, "+
				KEY_TYPE +" TEXT, "+
				KEY_UPDATED +" INTEGER, "+
				KEY_PICTURE +" TEXT, "+
				
				KEY_ANSWER_ID +" INTEGER, "+
				KEY_CONTENT +" TEXT, "+
				KEY_STATE +" TEXT NOT NULL"+
			");";
		
		public static final String[] INDEX_TABLE = {
			"CREATE INDEX "+ KEY_TITLE +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_TITLE +");",
			"CREATE INDEX "+ KEY_TYPE +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_TYPE +");",
			"CREATE INDEX "+ KEY_UPDATED +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_UPDATED +");",
			
			"CREATE INDEX "+ KEY_ANSWER_ID +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_ANSWER_ID +");",
			"CREATE INDEX "+ KEY_STATE +"_"+ TABLE_NAME +"_index ON "+ 
					TABLE_NAME +" ("+ KEY_STATE +");"
		};
		
		public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME +";";
	}
}
