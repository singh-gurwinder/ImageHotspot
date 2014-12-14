package org.sidhu.utility.imagehotspot.dao;

import org.sidhu.utility.imagehotspot.entity.Image;

public interface ImageDao {
	/**
	 * Function to save a new Image Object.
	 * @param image Image object to be persisted.
	 * @return Integer ID of the image being saved.
	 */
	public Integer save(Image image);
	
	/**
	 * Function to update an existing Image Object.
	 * @param image Image object to be updated.
	 * @return Integer ID of the image being updated.
	 */
	public Integer update(Image image);
	
	/**
	 * Function to delete an Image using  Id.
	 * @param imageId ID of the Image to be deleted.
	 * @return Integer ID of the image being deleted.
	 */
	public int delete(int imageId);
	
	/**
	 * Function to delete an Image Object.
	 * @param image Image Object to be deleted.
	 * @return Integer ID of the image being deleted.
	 */
	public int deleteImage(Image image);
	
	/**
	 * Function to find an Image using ID.
	 * @param imageId ID of the Image to search.
	 * @return Image Object.
	 */
	 
	public Image getImage(int imageId);
	
}
