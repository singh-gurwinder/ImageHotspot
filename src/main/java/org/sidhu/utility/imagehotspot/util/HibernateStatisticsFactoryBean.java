package org.sidhu.utility.imagehotspot.util;

import javax.persistence.EntityManagerFactory;

import org.hibernate.SessionFactory;
/**
 * Utility Class to Access Hibernate 2nd Level Cache Statistics
 */
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.hibernate.stat.QueryStatistics;
import org.hibernate.stat.SecondLevelCacheStatistics;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HibernateStatisticsFactoryBean {

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	private SessionFactory sessionFactory;

	/**
	 * Function to get Query Cache Statistics.
	 * @param query String query
	 * @return Cache Statistics as JSONObject
	 * @throws Exception if unable to retrieve Hibernate SessionFactory Class
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getQueryStatistics(String query) throws Exception {
		if (sessionFactory == null) {
			sessionFactory = ((HibernateEntityManagerFactory) entityManagerFactory)
					.getSessionFactory();
		}
		QueryStatistics queryStats = sessionFactory.getStatistics()
				.getQueryStatistics(query);
		JSONObject result = new JSONObject();
		// total hits on cache by this query.
		result.put("Cache Hit Count", queryStats.getCacheHitCount());
		// total misses on cache by this query.
		result.put("Cache Miss Count", queryStats.getCacheMissCount());
		// total number of objects put into cache by this query execution
		result.put("Cache Put Count", queryStats.getCachePutCount());
		// Number of times this query has been invoked
		result.put("Execution Count", queryStats.getExecutionCount());
		// average time for invoking this query.
		result.put("Execution Average Time", queryStats.getExecutionAvgTime());
		// maximum time incurred by query execution
		result.put("Execution Maximum Time", queryStats.getExecutionMaxTime());
		// minimum time incurred by query execution
		result.put("Execution Minimum Time", queryStats.getExecutionMinTime());
		// Number of rows returned over all invocations of this query
		result.put("Execution Row Count", queryStats.getExecutionRowCount());
		return result;
	}

	/**
	 * Function to get Second Level Cache Statistics
	 * @param cache String query
	 * @return Cache Statistics as JSONObject
	 * @throws Exception if unable to retrieve Hibernate SessionFactory Class
	 */
	@SuppressWarnings("unchecked")
	public JSONObject getSecondLevelCacheStatistics(String cache)
			throws Exception {
		if (sessionFactory == null) {
			sessionFactory = ((HibernateEntityManagerFactory) entityManagerFactory)
					.getSessionFactory();
		}
		SecondLevelCacheStatistics cacheStats = sessionFactory.getStatistics()
				.getSecondLevelCacheStatistics(cache);
		JSONObject result = new JSONObject();
		// total hits on cache by this query.
		result.put("Cache Hit Count", cacheStats.getHitCount());
		// total misses on cache by this query.
		result.put("Cache Miss Count", cacheStats.getMissCount());
		// total number of objects put into cache by this query execution
		result.put("Cache Put Count", cacheStats.getPutCount());
		// Number of times this query has been invoked
		result.put("Element Count in Memory",
				cacheStats.getElementCountInMemory());
		// average time for invoking this query.
		result.put("Element Count on Disk", cacheStats.getElementCountOnDisk());
		// maximum time incurred by query execution
		result.put("Size in Memory", cacheStats.getSizeInMemory());
		return result;
	}
}