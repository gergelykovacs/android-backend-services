package com.backend.services.strategies.storage;

import java.util.List;

import android.content.Context;

import com.backend.services.ServiceData;
import com.backend.services.connections.IServiceConnection;
import com.backend.services.events.ServiceEvent;
import com.backend.services.strategies.DefaultStrategy;
import com.backend.services.strategies.IStrategy;

public class StorageStrategyFactory {
	
	private StorageStrategyFactory() {
	}
	
	public static IStrategy getStrategy(ServiceEvent event, List<ServiceData> data, 
											IServiceConnection serviceConnection, Context context) {
		IStrategy strategy = null;
		switch(event) {
		case EVENT_GET_USER:
		case EVENT_REGISTER_USER:
		case EVENT_UPDATE_USER:
		case EVENT_DELETE_USER:
		case EVENT_SIGN_IN:
		case EVENT_SIGN_OUT:
			strategy = new UserStorageStrategy(event, data, serviceConnection, context);
			break;
		case EVENT_GET_QUESTIONS:
		case EVENT_ADD_QUESTION:
		case EVENT_DELETE_QUESTION:
			strategy = new QuestionStorageStrategy(event, data, serviceConnection, context);
			break;
		case EVENT_GET_ANSWERS:
		case EVENT_ADD_ANSWER:
		case EVENT_DELETE_ANSWER:
			strategy = new AnswerStorageStrategy(event, data, serviceConnection, context);
			break;
		default:
			strategy = new DefaultStrategy(event, data, serviceConnection, context);
			break;
	}
		return strategy;
	}

	@Override
	public final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
