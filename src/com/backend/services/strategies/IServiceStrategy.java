package com.backend.services.strategies;

import java.util.List;
import com.backend.services.ServiceData;

/**
 * Interface for the wrapper of the set up and call of strategies.
 * 
 * <p>Strategies might be cached and executed as a group 
 * each of them one after the other and the overall result may 
 * depend on the sub-results or a single strategy is given and executed. 
 * The interface does not make distinction between these cases it depends 
 * on the concrete implementation.</p>
 */
public interface IServiceStrategy {

	/**
	 * Sets up the current strategy for further execution.
	 * 
	 * @param strategy
	 * @return The reference pointing to the current strategy wrapper implementation.
	 */
	public IServiceStrategy setStrategy(IStrategy strategy);
	
	/**
	 * Initiates the execution of the given strategy(s).
	 * 
	 * @return The result given by the execution of the strategy(s). 
	 */
	public List<ServiceData> commit();
}
