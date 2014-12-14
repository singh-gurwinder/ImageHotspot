package org.sidhu.utility.imagehotspot.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.sidhu.utility.imagehotspot.entity.Image;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("imageDao")
public class ImageDaoImpl implements ImageDao{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Integer save(Image image) {
		em.persist(image);
		return image.getImageId();
	}

	@Override
	public Integer update(Image image) {
		Image transientImage = getImage(image.getImageId());
		transientImage.setHeight(image.getHeight());
		transientImage.setImageURL(image.getImageURL());
		transientImage.setWidth(image.getWidth());
		transientImage.setHotSpots(image.getHotSpots());
		return em.merge(transientImage).getImageId();
	}

	@Override
	public int delete(int imageId) {
		return deleteImage(getImage(imageId));
	}

	@Override
	public Image getImage(int imageId) {
		return em.find(Image.class, imageId);
	}

	@Override
	public int deleteImage(Image image) {
		em.remove(image);
		return image.getImageId();
	}

}
