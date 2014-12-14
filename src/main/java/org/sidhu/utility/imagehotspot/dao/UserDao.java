package org.sidhu.utility.imagehotspot.dao;

import org.sidhu.utility.imagehotspot.entity.User;

public interface UserDao {
	 /**
	  * Find User credentials in Database.
	  * @param username User name to be searched.
	  * @return User Object.
	  */
	public User findByUserName(String username);
	
	/**
	 * Save a User object in Database.
	 * @param user User Object to be persisted.
	 */
	public void save(User user);
	
	/**
	 * Checks if a User already exist. This method is used by User Validator to check if a User already exist while creating a new User.
	 * @param username User name to be searched.
	 * @return boolean value. true if User already exists, false otherwise.
	 */
	public boolean userExists(String username);
 
}
