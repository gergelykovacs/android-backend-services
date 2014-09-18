package com.backend.services.db.loaders;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.backend.services.datamodels.UserModel;
import com.backend.services.db.DatabaseSchema;
import com.backend.services.utils.StorageUtils;

public class UserLoader 
				extends BaseLoader<UserModel> implements ILoader<UserModel> {
	
	private static final boolean USE_ENCRYPTED_PASSWORD = false;
	
	private static final int USERNAME_POSITION = 0;
	private static final int PASSWORD_POSITION = 0;
		
	public UserLoader(Context context) {
		super(context);
	}	

	@Override
	public boolean create(UserModel dataModel) {
		return mDb.insert(
				DatabaseSchema.User.TABLE_NAME, 
				null, 
				getContentValuesFromDataModel(dataModel)
			) > -1;
	}

	@Override
	public boolean update(UserModel dataModel) {
		return mDb.update(
				DatabaseSchema.User.TABLE_NAME, 
				getContentValuesFromDataModel(dataModel), 
				DatabaseSchema.User.KEY_ID +"=?", 
				new String[] {Integer.toString(dataModel.getId())}
			) > 0;
	}

	@Override
	public boolean delete(UserModel dataModel) {
		return mDb.delete(
				DatabaseSchema.User.TABLE_NAME, 
				DatabaseSchema.User.KEY_ID +"=?", 
				new String[] {Integer.toString(dataModel.getId())}
			) > 0;
	}

	@Override
	public boolean delete(int id) {
		return mDb.delete(
				DatabaseSchema.User.TABLE_NAME, 
				DatabaseSchema.User.KEY_ID +"=?", 
				new String[] {Integer.toString(id)}
			) > 0;
	}

	@Override
	public UserModel fetch(Fetch fetchBy, int i, long l, String[] s) {
		String selection = null;
		String[] selectionArgs = null; 
		switch(fetchBy) {
			case BY_ID:
				selection = DatabaseSchema.User.KEY_ID +"=?";
				selectionArgs = new String[] {Integer.toString(i)};
				break;
			case BY_USER_ID:
				selection = DatabaseSchema.User.KEY_USER_ID +"=?";
				selectionArgs = new String[] {Integer.toString(i)};
				break;
			case BY_USERNAME_PASSWORD:
				selection = DatabaseSchema.User.KEY_USER_ID +"=?";
				try {
					selectionArgs = new String[] {
							s[USERNAME_POSITION], 
							(USE_ENCRYPTED_PASSWORD ? 
								StorageUtils.Crypto.encrypt(mContext, s[PASSWORD_POSITION]) : 
								s[PASSWORD_POSITION])
					};
				} catch (Exception e) {
					e.printStackTrace();
				}
			default:
				break;
		}
		if(selection != null) {
			Cursor c = mDb.query (
				DatabaseSchema.User.TABLE_NAME, 
				null, 
				selection, 
				selectionArgs, 
				null, null, null, null
			);
			return getDataModelFromCursor(c);
		}
		return null;
	}

	@Override
	public List<UserModel> rawFetch(String query, String[] queryArgs) {
		return null;
	}

	@Override
	public List<UserModel> fetchAll() {
		Cursor c = mDb.query (DatabaseSchema.User.TABLE_NAME, null, null, null, null, null, null, null);
		return getDataModelListFromCursor(c);
	}

	@Override
	public List<UserModel> fetchAll(String orderBy) {
		return null;
	}

	@Override
	public List<UserModel> fetchAll(String where, String[] whereArgs) {
		return null;
	}

	@Override
	public List<UserModel> fetchAll(String where, String[] whereArgs, String orderBy) {
		return null;
	}
	
	@Override
	protected ContentValues getContentValuesFromDataModel(UserModel dataModel) {
		ContentValues values = new ContentValues();
		
		values.put(DatabaseSchema.User.KEY_USER_ID, dataModel.getUserId());
		values.put(DatabaseSchema.User.KEY_EMAIL, dataModel.getEmail());
		values.put(DatabaseSchema.User.KEY_USERNAME, dataModel.getUsername());
		values.put(DatabaseSchema.User.KEY_PASSWORD, dataModel.getPassword());
		values.put(DatabaseSchema.User.KEY_SESSION_TOKEN, dataModel.getSessionToken());
		values.put(DatabaseSchema.User.KEY_REGISTERED, dataModel.getRegistered());
		values.put(DatabaseSchema.User.KEY_UPDATED, dataModel.getUpdated());
		values.put(DatabaseSchema.User.KEY_PROFILE_PICTURE, dataModel.getProfilePicture());
		
		return values;
	}
	
	@Override
	protected List<UserModel> getDataModelListFromCursor(Cursor cursor) {
		List<UserModel> list = new ArrayList<UserModel>();
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false) {
			list.add(getDataModelFromCursor(cursor));
			cursor.moveToNext();
		}
		return list;
	}
	
	@Override
	protected UserModel getDataModelFromCursor(Cursor cursor) {
		return new UserModel(
				cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), 
				cursor.getString(4), cursor.getString(5), cursor.getLong(6), cursor.getLong(7),
				cursor.getString(8)
			);
	}
}
