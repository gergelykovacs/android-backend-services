package com.backend.services.db.loaders;

import java.util.ArrayList;
import java.util.List;

import com.backend.services.datamodels.QuestionModel;
import com.backend.services.db.DatabaseSchema;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class QuestionLoader 
				extends BaseLoader<QuestionModel> implements ILoader<QuestionModel> {

	public QuestionLoader(Context context) {
		super(context);
	}

	@Override
	public boolean create(QuestionModel dataModel) {
		return mDb.insert(
				DatabaseSchema.Question.TABLE_NAME, 
				null, 
				getContentValuesFromDataModel(dataModel)
			) > -1;
	}

	@Override
	public boolean update(QuestionModel dataModel) {
		return mDb.update(
				DatabaseSchema.Question.TABLE_NAME, 
				getContentValuesFromDataModel(dataModel), 
				DatabaseSchema.Question.KEY_ID +"=?", 
				new String[] {Integer.toString(dataModel.getId())}
			) > 0;
	}

	@Override
	public boolean delete(QuestionModel dataModel) {
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
	public QuestionModel fetch(Fetch fetchBy, int i, long l, String[] s) {
		return null;
	}

	@Override
	public List<QuestionModel> rawFetch(String query, String[] queryArgs) {
		return null;
	}

	@Override
	public List<QuestionModel> fetchAll() {
		Cursor c = mDb.query (DatabaseSchema.Question.TABLE_NAME, null, null, null, null, null, null, null);
		return getDataModelListFromCursor(c);
	}

	@Override
	public List<QuestionModel> fetchAll(String orderBy) {
		return null;
	}

	@Override
	public List<QuestionModel> fetchAll(String where, String[] whereArgs) {
		return null;
	}

	@Override
	public List<QuestionModel> fetchAll(String where, String[] whereArgs,	String orderBy) {
		return null;
	}

	@Override
	protected ContentValues getContentValuesFromDataModel(QuestionModel dataModel) {
		ContentValues values = new ContentValues();
		
		values.put(DatabaseSchema.Question.KEY_TITLE, dataModel.getTitle());
		values.put(DatabaseSchema.Question.KEY_DESCRIPTION, dataModel.getDescription());
		values.put(DatabaseSchema.Question.KEY_TYPE, dataModel.getType());
		values.put(DatabaseSchema.Question.KEY_UPDATED, dataModel.getUpdated());
		values.put(DatabaseSchema.Question.KEY_PICTURE, dataModel.getPicture());
		
		values.put(DatabaseSchema.Question.KEY_QUESTION_ID, dataModel.getQuestionId());
		
		return values;
	}

	@Override
	protected List<QuestionModel> getDataModelListFromCursor(Cursor cursor) {
		List<QuestionModel> list = new ArrayList<QuestionModel>();
		cursor.moveToFirst();
		while(cursor.isAfterLast() == false) {
			list.add(getDataModelFromCursor(cursor));
			cursor.moveToNext();
		}
		return list;
	}

	@Override
	protected QuestionModel getDataModelFromCursor(Cursor cursor) {
		return new QuestionModel(
				cursor.getInt(0), cursor.getString(1), cursor.getString(2), 
				cursor.getString(3), cursor.getLong(4), cursor.getString(5), 
				cursor.getInt(6)
			);
	}
}
