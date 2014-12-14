package org.sidhu.utility.imagehotspot.dao;

import java.util.List;

import org.sidhu.utility.imagehotspot.entity.Product;

public interface ProductDao {
	/**
	 * Function to save a new Product Object.
	 * @param product Product Object to be persisted.
	 * @return ID of the Product.
	 */
	public Integer save(Product product);
	
	/**
	 * Function to update an existing product Description/Name.
	 * @param product Product Object to be updated.
	 * @return ID of the Product.
	 */
	public Integer update(Product product);
	
	/**
	 * Function to update an existing product Images.
	 * @param product Product Object to be updated.
	 * @return ID of the Product.
	 */
	public Integer updateProductImages(Product product);
	
	/**
	 * Function to retrieve all Products.
	 * @return List of Product Objects.
	 */
	public List<Product> getProductsList();
	
	/**
	 * Function to retrieve Product using Product ID.
	 * @param id ID of the Product to retrieve.
	 * @return Product Object.
	 */
	public Product getProduct(int id);
	
	/**
	 * Function to delete a Product.
	 * @param productId ID of the Product to be deleted.
	 * @return int ID of the Product.
	 */
	public int delete(Integer productId);
	
	/**
	 * Function to search products using Keywords List.
	 * @param keywordsList String Keyword List to be searched.
	 * @return List of Product Objects.
	 */
	public List<Product> searchProducts(List<String> keywordsList);

	/**
	 * @author Sidhu
	 * Function to check if product already exist.
	 * @param productName Name of the Product to be checked. This method is used by Product Validator.
	 * @return boolean value. true if Product already exists, false otherwise.
	 */
	public boolean productExists(String productName);
}
