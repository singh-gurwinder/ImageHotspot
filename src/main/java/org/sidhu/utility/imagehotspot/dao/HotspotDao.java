package org.sidhu.utility.imagehotspot.dao;

import org.sidhu.utility.imagehotspot.entity.HotSpot;

public interface HotspotDao {
	/**
	 * Function to save a new Hotspot.
	 * @param hotspot HotSpot Object to be persisted.
	 * @return Integer ID of the new HotSpot Object.
	 */
	public Integer save(HotSpot hotspot);
	
	/**
	 * Function to update an existing Hotspot.
	 * @param hotspot HotSpot Object to be updated.
	 * @return Integer ID of the updated HotSpot Object.
	 */
	public Integer update(HotSpot hotspot);
	
	/**
	 * Function to delete a Hotspot using Id.
	 * @param hotspotId ID of the HotSpot Object to be deleted.
	 * @return Integer ID of the deleted HotSpot Object.
	 */
	public int delete(int hotspotId);
	
	/**
	 * Function to delete a Hotspot.
	 * @param hotspot HotSpot Object to be deleted.
	 * @return Integer ID of the updated HotSpot Object.
	 */
	public int deleteHotspot(HotSpot hotspot);
	
	/**
	 * Function to find a Hotspot using ID.
	 * @param hotspotId ID of the HotSpot Object to Search.
	 * @return HotSpot Object.
	 */
	public HotSpot getHotspot(int hotspotId);
	
}
