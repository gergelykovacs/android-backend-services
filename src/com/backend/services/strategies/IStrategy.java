package com.backend.services.strategies;

import java.util.List;
import com.backend.services.ServiceData;

/**
 * Interface for strategies.
 */
public interface IStrategy {
	
	/**
	 * Evaluates the concrete operation(s) given by the implementation.
	 * 
	 * @return The result of the operation(s).
	 */
	public List<ServiceData> process();
}
