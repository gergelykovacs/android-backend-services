package com.backend.services.db.loaders;

import java.util.List;

/**
 * Interface for database loaders with respect to CRUD operations.
 * 
 * @param <M> The data model.
 */
public interface ILoader<M> {

	/**
	 * Opens the database connection.
	 */
	public void open();
	
	/**
	 * Closes the current database connection.
	 */
	public void close();
	
	/**
	 * Creates a new entry.
	 * 
	 * @param dataModel The model from which a new entry should be created.
	 * @return true on success otherwise false.
	 */
	public boolean create(M dataModel);
	
	/**
	 * Updates an existing entry given by the data model.
	 * 
	 * @param dataModel The model that is the base of the update procedure.
	 * @return true on success otherwise false.
	 */
	public boolean update(M dataModel);
	
	/**
	 * Deletes an entry described by the data model.
	 * 
	 * @param dataModel The model.
	 * @return true on success otherwise false.
	 */
	public boolean delete(M dataModel);
	
	/**
	 * Deletes an entry described by the ID field.
	 * 
	 * @param id The ID against which to look up.
	 * @return true on success otherwise false.
	 */
	public boolean delete(int id);
	
	/**
	 * Fetches a single row relevant to the parameters which 
	 * meaning may depend on the concrete implementation and 
	 * converts the result to the appropriate data model.
	 * 
	 * @param fetchBy enum
	 * @param i int
	 * @param l long
	 * @param s String[]
	 * @return The resulting model.
	 */
	public M fetch(Fetch fetchBy, int i, long l, String[] s);
	
	/**
	 * Perform a raw query and gives back the model list 
	 * created from the query result.
	 * 
	 * @param query The query.
	 * @param queryArgs The parameters in the query. (See: Android SQLite documentation.)
	 * @return The list of data models.
	 */
	public List<M> rawFetch(String query, String[] queryArgs);
	
	/**
	 * Fetches all existing data.
	 * 
	 * @return The list of data models.
	 */
	public List<M> fetchAll();
	
	/**
	 * Fetches all existing data with the given order parameter. 
	 * (See: Android SQLite documentation.)
	 * 
	 * @return The list of data models.
	 */
	public List<M> fetchAll(String orderBy);
	
	/**
	 * Fetches all existing data with the given filter parameters.
	 * (See: Android SQLite documentation.)
	 * 
	 * @param where
	 * @param whereArgs
	 * @return The list of data models.
	 */
	public List<M> fetchAll(String where, String[] whereArgs);
	
	/**
	 * Fetches all existing data with the given filter and order parameters.
	 * (See: Android SQLite documentation.)
	 * 
	 * @param where
	 * @param whereArgs
	 * @param orderBy
	 * @return The list of data models.
	 */
	public List<M> fetchAll(String where, String[] whereArgs, String orderBy);
}
