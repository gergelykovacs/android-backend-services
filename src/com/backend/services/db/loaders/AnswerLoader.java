package com.backend.services.db.loaders;

import java.util.ArrayList;
import java.util.List;

import com.backend.services.datamodels.AnswerModel;
import com.backend.services.db.DatabaseSchema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class AnswerLoader 
				extends BaseLoader<AnswerModel> implements ILoader<AnswerModel> {

	public AnswerLoader(Context context) {
		super(context);
	}

	@Override
	public boolean create(AnswerModel dataModel) {
		return mDb.insert(
				DatabaseSchema.Question.TABLE_NAME, 
				null, 
				getContentValuesFromDataModel(dataModel)
			) > -1;
	}

	@Override
	public boolean update(AnswerModel dataModel) {
		return mDb.update(
				DatabaseSchema.Question.TABLE_NAME, 
				getContentValuesFromDataModel(dataModel), 
				DatabaseSchema.Question.KEY_ID +"=?", 
				new String[] {Integer.toString(dataModel.getId())}
			) > 0;
	}

	@Override
	public boolean delete(AnswerModel dataModel) {
		return mDb.delete(
				DatabaseSchema.Question.TABLE_NAME, 
				DatabaseSchema.Question.KEY_ID +"=?", 
				new String[] {Integer.toString(dataModel.getId())}
			) > 0;
	}

	@Override
	public boolean delete(int id) {
		return mDb.delete(
				DatabaseSchema.Question.TABLE_NAME, 
				DatabaseSchema.Question.KEY_ID +"=?", 
				new String[] {Integer.toString(id)}
			) > 0;
	}

	@Override
	public AnswerModel fetch(Fetch fetchBy, int i, long l, String[] s) {
		return null;
	}

	@Override
	public List<AnswerModel> rawFetch(String query, String[] queryArgs) {
		return null;
	}

	@Override
	public List<AnswerModel> fetchAll() {
		Cursor c = mDb.query (DatabaseSchema.Question.TABLE_NAME, null, null, null, null, null, null, null);
		return getDataModelListFromCursor(c);
	}

	@Override
	public List<AnswerModel> fetchAll(String orderBy) {
		return null;
	}

	@Override
	public List<AnswerModel> fetchAll(String where, String[] whereArgs) {
		return null;
	}

	@Override
	public List<AnswerModel> fetchAll(String where, String[] whereArgs,	String orderBy) {
		return null;
	}

	@Override
	protected ContentValues getContentValuesFromDataModel(AnswerModel dataModel) {
		ContentValues values = new ContentValues();
		
		values.put(DatabaseSchema.Answer.KEY_TITLE, dataModel.getTitle());
		values.put(DatabaseSchema.Answer.KEY_DESCRIPTION, dataModel.getDescription());
		values.put(DatabaseSchema.Answer.KEY_TYPE, dataModel.getType());
		values.put(DatabaseSchema.Answer.KEY_UPDATED, dataModel.getUpdated());
		values.put(DatabaseSchema.Answer.KEY_PICTURE, dataModel.getPicture());
		
		values.put(DatabaseSchema.Answer.KEY_ANSWER_ID, dataModel.getAnswerId());
		values.put(DatabaseSchema.Answer.KEY_CONTENT, dataModel.getContent());
		values.put(DatabaseSchema.Answer.KEY_STATE, dataModel.getState());
		
		return values;
	}

	@Override
	protected List<AnswerModel> getDataModelListFromCursor(Cursor cursor) {
		List<AnswerModel> list = new ArrayList<AnswerModel>();
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false) {
			list.add(getDataModelFromCursor(cursor));
			cursor.moveToNext();
		}
		return list;
	}

	@Override
	protected AnswerModel getDataModelFromCursor(Cursor cursor) {
		return new AnswerModel(
				cursor.getInt(0), cursor.getString(1), cursor.getString(2), 
				cursor.getString(3), cursor.getLong(4), cursor.getString(5), 
				cursor.getInt(6), cursor.getString(7), cursor.getString(8)
			);
	}
}
