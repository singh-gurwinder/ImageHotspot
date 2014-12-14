package org.sidhu.utility.imagehotspot.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.sidhu.utility.imagehotspot.entity.HotSpot;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("hotspotDao")
public class HotspotDaoImpl implements HotspotDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Integer save(HotSpot hotspot) {
		em.persist(hotspot);
		return hotspot.getHotspotId();
	}

	@Override
	public Integer update(HotSpot hotspot) {
		HotSpot transientHotspot = getHotspot(hotspot.getHotspotId());
		transientHotspot.setKeywords(hotspot.getKeywords());
		transientHotspot.setLeft(hotspot.getLeft());
		transientHotspot.setTop(hotspot.getTop());
		return em.merge(transientHotspot).getHotspotId();
	}

	@Override
	public int delete(int hotspotId) {
		return deleteHotspot(getHotspot(hotspotId));
	}

	@Override
	public HotSpot getHotspot(int hotspotId) {
		return em.find(HotSpot.class, hotspotId);
	}

	@Override
	public int deleteHotspot(HotSpot hotspot) {
		em.remove(hotspot);
		return hotspot.getHotspotId();
	}

}
