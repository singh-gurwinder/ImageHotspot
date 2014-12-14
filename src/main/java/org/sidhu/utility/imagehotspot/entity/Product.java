package org.sidhu.utility.imagehotspot.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Entity
public class Product implements Serializable {

	/**
	 * Product Entity
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PRODUCT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;

	@Column(name = "PRODUCT_NAME")
	private String productName;
	
	@Column(name = "PRODUCT_DESCRIPTION")
	private String description;
	
	@Version
	@Column(name = "PRODUCT_VERSION")
	private Integer version;

	@OneToMany(cascade = CascadeType.PERSIST , orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<Image> images = new HashSet<Image>();

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Converts a Product Object to JSONObject.
	 * @return Product as JSONObject.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject prodJSON = new JSONObject();
		Iterator<Image> iterator = this.getImages().iterator();
		if (iterator.hasNext()) {
			prodJSON.put("Images", new JSONArray());
			while (iterator.hasNext()) {
				Image img = iterator.next();
				JSONArray array = (JSONArray) prodJSON.get("Images");
				array.add(img.toJSON());
			}
		}
		prodJSON.put("Description", this.getDescription());
		prodJSON.put("Name", this.getProductName());
		prodJSON.put("ID", this.getProductId());
		return prodJSON;
	}

	/**
	 * Converts a HOtSpot Object to String.
	 * @return HotSpot as String.
	 */
	@Override
	public String toString() {
		return "{\"Product\":" + this.toJSON().toString() + "}";
	}
}
