package com.backend.services.db.loaders;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.backend.services.db.DatabaseHelper;

/**
 * Base Loader
 * 
 * <p>Reusable loader with minimal functionalities. 
 * It implements only the open and close logic and stores the 
 * relevant references.</p>
 * 
 * @param <M> The data model. 
 */
public abstract class BaseLoader<M> {

	protected Context mContext;
	protected DatabaseHelper mDbHelper;
	protected SQLiteDatabase mDb;
	
	public BaseLoader(Context context) {
		mContext = context;
	}
	
	public void open() {
		if(mDbHelper == null && mDb == null) {
			mDbHelper = new DatabaseHelper(mContext);
			mDb = mDbHelper.getWritableDatabase();
		}
	}

	public void close() {
		if(mDbHelper != null) {
			mDbHelper.close();
			mDb = null;
			mDbHelper = null;
		}
	}
	
	/**
	 * Implements the logic to convert the given data model into 
	 * database friendly content values.
	 *  
	 * @param dataModel The seed from which to generate database friendly structure.
	 * @return The content values.
	 */
	protected abstract ContentValues getContentValuesFromDataModel(M dataModel);
	
	/**
	 * Implements the logic to generate a list of models from the cursor 
	 * given by a database query.
	 *   
	 * @param cursor The result of a query.
	 * @return The list of models.
	 */
	protected abstract List<M> getDataModelListFromCursor(Cursor cursor);
	
	/**
	 * Generates a concrete data model from the given cursor.
	 *  
	 * @param cursor The cursor at a given position.
	 * @return The model generated from the current cursor.
	 */
	protected abstract M getDataModelFromCursor(Cursor cursor);
}
