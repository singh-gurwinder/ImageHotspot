package org.sidhu.utility.imagehotspot.dao;

import java.util.List;

import org.sidhu.utility.imagehotspot.entity.KeywordList;

public interface KeywordDao {
	/**
	 * Function to save a new Keyword.
	 * @param keyword KeywordList Object to be persisted.
	 * @return Integer ID of the KeywordList Object.
	 */
	public Integer save(KeywordList keyword);
	
	/**
	 * Function to update an existing Keyword.
	 * @param keyword KeywordList Object to be updated.
	 * @return Integer ID of the KeywordList Object.
	 */
	public Integer update(KeywordList keyword);
	
	/**
	 * Function to delete a Keyword using ID.
	 * @param keywordId ID of the KeywordList Object being deleted.
	 * @return int ID of the KeywordList Object being deleted.
	 */
	public int delete(int keywordId);
	
	/**
	 * Function to retrieve all Keyword.
	 * @return List of KeywordList Objects.
	 */
	public List<KeywordList> getKeywordsList();
	
	/**
	 * Function to find a Keyword with ID.
	 * @param id ID of the KeywordList Object.
	 * @return KeywordList Object.
	 */
	public KeywordList getKeyword(int id);
	
	/**
	 * Function to check if a Keyword already exist. This function is used by Keyword Validator.
	 * @param description Description of the Keyword.
	 * @param group Group of the Keyword.
	 * @return boolean value. true if Keyword already exists, false otherwise.
	 */
	public boolean keywordExists(String description, String group);
}
