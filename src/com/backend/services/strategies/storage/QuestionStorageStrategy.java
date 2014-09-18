package com.backend.services.strategies.storage;

import java.util.List;

import android.content.Context;

import com.backend.services.ServiceData;
import com.backend.services.connections.IServiceConnection;
import com.backend.services.datamodels.QuestionModel;
import com.backend.services.db.loaders.QuestionLoader;
import com.backend.services.events.ServiceEvent;
import com.backend.services.strategies.IStrategy;

public class QuestionStorageStrategy implements IStrategy {
	
	//private static final String TAG = ProgramStorageStrategy.class.getSimpleName();
	
	@SuppressWarnings("unused")
	private ServiceEvent mEvent;
	private List<ServiceData> mData;
	@SuppressWarnings("unused")
	private IServiceConnection mServiceConnection;
	@SuppressWarnings("unused")
	private Context mContext;
	
	@SuppressWarnings("unused")
	private QuestionLoader mLoader;
	@SuppressWarnings("unused")
	private List<QuestionModel> mQuestions;
	
	public QuestionStorageStrategy() {
	}
	
	public QuestionStorageStrategy(ServiceEvent event, List<ServiceData> data, 
						IServiceConnection serviceConnection, Context context) {
		mEvent = event;
		mData = data;
		mServiceConnection = serviceConnection;
		mContext = context;
	}

	@Override
	public List<ServiceData> process() {
		// Do things
		return mData;
	}
}
