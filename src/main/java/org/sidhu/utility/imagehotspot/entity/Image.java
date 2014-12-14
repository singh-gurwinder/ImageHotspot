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

public class Image implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "IMAGE_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int imageId;
	
	@Column(name = "IMAGE_URL")
	private String imageURL;
	
	@Column(name = "IMAGE_HEIGHT")
	private int height;
	
	@Column(name = "IMAGE_WIDTH")
	private int width;
	
	@Version
	@Column(name = "IMAGE_VERSION")
	private Integer version;

	@OneToMany( fetch=FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval=true)
	private Set<HotSpot> hotSpots = new HashSet<HotSpot>();

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public Set<HotSpot> getHotSpots() {
		return hotSpots;
	}

	public void setHotSpots(Set<HotSpot> hotSpots) {
		this.hotSpots = hotSpots;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Converts an Image Object to JSONObject.
	 * @return Image as JSONObject.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject prodJSON = new JSONObject();
		Iterator<HotSpot> iterator=this.getHotSpots().iterator();
		if (iterator.hasNext()) {
			prodJSON.put("Hotspots", new JSONArray());
			while (iterator.hasNext()) {
				HotSpot hs = iterator.next();
				JSONArray array = (JSONArray) prodJSON.get("Hotspots");
				array.add(hs.toJSON());
			}
		}

		prodJSON.put("Height", this.getHeight());
		prodJSON.put("Width", this.getWidth());
		prodJSON.put("URL", this.getImageURL());
		prodJSON.put("ID", this.getImageId());
		return prodJSON;
	}
}
