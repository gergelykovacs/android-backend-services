package com.backend.services.strategies;

import java.util.List;
import com.backend.services.ServiceData;

/**
 * Service Strategy
 * 
 * <p>The implementation of the service strategy logic uses, accepts only 
 * a single strategy to be executed.</p>
 */
public class ServiceStrategy implements IServiceStrategy {
	
	private IStrategy mStrategy;

	public ServiceStrategy() {
	}
	
	public ServiceStrategy(IStrategy strategy) {
		mStrategy = strategy;
	}

	@Override
	public IServiceStrategy setStrategy(IStrategy strategy) {
		mStrategy = strategy;
		return this;
	}

	@Override
	public List<ServiceData> commit() {
		return mStrategy.process();
	}
}
