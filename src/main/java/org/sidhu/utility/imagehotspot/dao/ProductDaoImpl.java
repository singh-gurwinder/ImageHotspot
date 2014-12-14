package org.sidhu.utility.imagehotspot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.sidhu.utility.imagehotspot.entity.KeywordList;
import org.sidhu.utility.imagehotspot.entity.Product;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	private EntityManager em;

	public Integer save(Product product) {
		em.persist(product);
		return product.getProductId();
	}

	public Integer update(Product product) {
		Product transientProduct = getProduct(product.getProductId());
		transientProduct.setProductName(product.getProductName());
		transientProduct.setDescription(product.getDescription());
		return em.merge(transientProduct).getProductId();
	}
	
	public Integer updateProductImages(Product product) {
		Product transientProduct = getProduct(product.getProductId());
		transientProduct.setImages(product.getImages());
		return em.merge(transientProduct).getProductId();
	}

	public List<Product> getProductsList() {
		CriteriaQuery<Product> criteriaQuery = em.getCriteriaBuilder().createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		criteriaQuery.select(root);
		return em.createQuery(criteriaQuery).getResultList();
	}

	public Product getProduct(int id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
		Root<Product> root = criteriaQuery.from(Product.class);
		Path<Integer> path = root.<Integer> get("productId");
		criteriaQuery.where(criteriaBuilder.equal(path, id));
		return em.createQuery(criteriaQuery).getSingleResult();
	}

	public int delete(Integer productId) {
		Query q = em.createQuery("delete from Product p where p.productId=:productId");
		q.setParameter("productId", productId);
		return q.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> searchProducts(List<String> keywordsList) {
		Query q = em.createQuery("select distinct p from Product as p join p.images as i join i.hotSpots as h join h.keywords as k where k.description in (:keylist)");
		q.setParameter("keylist", keywordsList);
		return q.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean productExists(String productName) {
		Query q = em.createQuery("from Product p where p.productName = :productName", Product.class);
		q.setParameter("productName", productName);
		List<KeywordList> result = q.getResultList();
		if (result.isEmpty())
			return false;
		else
			return true;
	}
}
