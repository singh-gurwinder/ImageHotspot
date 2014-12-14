package org.sidhu.utility.imagehotspot.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.sidhu.utility.imagehotspot.entity.KeywordList;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("keywordDao")
public class KeywordDaoImpl implements KeywordDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Integer save(KeywordList keyword) {
		em.persist(keyword);
		return keyword.getKeywordId();
	}

	@Override
	public Integer update(KeywordList keyword) {
		KeywordList transientKeyword = getKeyword(keyword.getKeywordId());
		transientKeyword.setDescription(keyword.getDescription());
		transientKeyword.setKeywordGroup(keyword.getKeywordGroup());
		transientKeyword.setRanking(keyword.getRanking());
		return em.merge(transientKeyword).getKeywordId();
	}

	@Override
	public int delete(int keywordId) {
		Query q=em.createQuery("delete from KeywordList k where k.keywordId=:keywordId");
		q.setParameter("keywordId", keywordId);
		return q.executeUpdate();
	}

	@Override
	public List<KeywordList> getKeywordsList() {
		return em.createQuery("from KeywordList k", KeywordList.class).getResultList();
	}

	@Override
	public KeywordList getKeyword(int id) {
		return em.find(KeywordList.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean keywordExists(String description, String group) {
		Query q = em.createQuery("from KeywordList k where k.description = :description and k.keywordGroup = :keywordGroup", KeywordList.class);
		q.setParameter("description", description);
		q.setParameter("keywordGroup", group);
		List<KeywordList> result = q.getResultList();
		if (result.isEmpty())
			return false;
		else
			return true;
	}
}
