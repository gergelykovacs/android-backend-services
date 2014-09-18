package com.backend.services.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The SQLite database connection helper class that creates 
 * the connection and the entire database at the first time 
 * or update it.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context) {
		super(context, DatabaseSchema.DATABASE_NAME, null, DatabaseSchema.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		for(String createTable : DatabaseSchema.CREATE_ALL) {
			db.execSQL(createTable);
		}
		for(String[] indexCollection : DatabaseSchema.INDEX_ALL) {
			for(String createIndex : indexCollection) {
				db.execSQL(createIndex);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		for(String dropTable : DatabaseSchema.DROP_ALL) {
			db.execSQL(dropTable);
		}
		onCreate(db);
	}
}
