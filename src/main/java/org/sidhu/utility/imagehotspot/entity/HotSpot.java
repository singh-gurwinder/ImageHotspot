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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import org.hibernate.annotations.SortNatural;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@Entity
public class HotSpot implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "HOTSPOT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int hotspotId;

	@Column(name="LEFT_OFFSET")
	private int left;
	
	@Column(name = "TOP_OFFSET")
	private int top;
	
	@Version
	@Column(name = "HOTSPOT_VERSION")
	private Integer version;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "HOTSPOT_KEYWORDS_LINK", joinColumns = { @JoinColumn(name = "HOTSPOT_ID") }, inverseJoinColumns = { @JoinColumn(name = "KEYWORD_ID") })
	@SortNatural
	private Set<KeywordList> keywords = new HashSet<KeywordList>();

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public int getHotspotId() {
		return hotspotId;
	}

	public void setHotspotId(int hotspotId) {
		this.hotspotId = hotspotId;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public Set<KeywordList> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<KeywordList> keywords) {
		this.keywords = keywords;
	}

	/**
	 * Converts a HotSpot Object to JSONObject.
	 * @return HotSpot as JSONObject.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject prodJSON = new JSONObject();
		Iterator<KeywordList> iterator=this.getKeywords().iterator();
		if (iterator.hasNext()) {
			prodJSON.put("Keywords", new JSONArray());
			while (iterator.hasNext()) {
				KeywordList keyword = iterator.next();
				JSONArray array = (JSONArray) prodJSON.get("Keywords");
				array.add(keyword.toJSON());
			}
		}
		prodJSON.put("Top", this.getTop());
		prodJSON.put("Left", this.getLeft());
		prodJSON.put("ID", this.getHotspotId());
		return prodJSON;
	}
}
