package org.sidhu.utility.imagehotspot.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import org.json.simple.JSONObject;

@Entity
public class KeywordList implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "KEYWORD_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int keywordId;
	
	@Version
	@Column(name = "KEYWORD_VERSION")
	private Integer version;
	
	@Column(name = "KEYWORD_DESCRIPTION")
	private String description;

	@Column(name = "KEYWORD_GROUP")
	private String keywordGroup;
	
	@Column(name = "KEYWORD_RANKING")
	private int ranking;

	@ManyToMany(mappedBy = "keywords", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
	private Set<HotSpot> hotspots = new HashSet<HotSpot>();

	public int getKeywordId() {
		return keywordId;
	}

	public void setKeywordId(int keywordId) {
		this.keywordId = keywordId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywordGroup() {
		return keywordGroup;
	}

	public void setKeywordGroup(String keywordGroup) {
		this.keywordGroup = keywordGroup;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public Set<HotSpot> getHotspots() {
		return hotspots;
	}

	public void setHotspots(Set<HotSpot> hotspots) {
		this.hotspots = hotspots;
	}
	
	/**
	 * Converts a KeywordList Object to JSONObject.
	 * @return KeywordList as JSONObject.
	 */
	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject prodJSON = new JSONObject();
		prodJSON.put("Ranking", this.getRanking());
		prodJSON.put("Group", this.getKeywordGroup());
		prodJSON.put("Description", this.getDescription());
		prodJSON.put("ID", this.getKeywordId());
		return prodJSON;
	}
}
