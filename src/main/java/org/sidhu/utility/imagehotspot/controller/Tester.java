package org.sidhu.utility.imagehotspot.controller;

import java.util.HashSet;
import java.util.Set;

import org.json.simple.JSONObject;
import org.sidhu.utility.imagehotspot.dao.ProductDao;
import org.sidhu.utility.imagehotspot.entity.HotSpot;
import org.sidhu.utility.imagehotspot.entity.Image;
import org.sidhu.utility.imagehotspot.entity.KeywordList;
import org.sidhu.utility.imagehotspot.entity.Product;
import org.sidhu.utility.imagehotspot.util.HibernateStatisticsFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/home", method = RequestMethod.GET)
public class Tester {
	@Autowired
	private ProductDao productDao;
	@Autowired
	HibernateStatisticsFactoryBean hibernateStatisticsFactoryBean;

	public Tester() {
		// No argument Constructor
	}

	/**
	 * Create Test Products and persist to database.
	 * @return JSP View name as String
	 */
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save() {
		// Product 1
		Product prod1 = new Product();
		prod1.setDescription("IPhone Mobile");
		prod1.setProductName("IPhone 6 Plus");

		// Product 1 Image (Image 1)
		Image img1 = new Image();
		img1.setImageURL("http://cdn.wonderfulengineering.com/wp-content/uploads/2014/04/iphone6_screen-5.jpg");
		img1.setHeight(418);
		img1.setWidth(617);

		// Product 1 Image 1 HotSpot (HotSpot 1)
		HotSpot hotSpot1 = new HotSpot();
		hotSpot1.setTop(50);
		hotSpot1.setLeft(100);

		// Product 1 HotSpot 1 KeywordList (Keyword 1)
		KeywordList key1 = new KeywordList();
		key1.setKeywordGroup("Manufacturer");
		key1.setDescription("Apple");
		key1.setRanking(2);

		// Product 1 HotSpot 1 KeywordList (Keyword 2)
		KeywordList key2 = new KeywordList();
		key2.setKeywordGroup("Network");
		key2.setDescription("4G");
		key2.setRanking(5);

		//Product 1 ImageSet (ImageSet 1)
		Set<Image> imgSet1 = new HashSet<Image>();
		imgSet1.add(img1);

		//Product 1 Image 1 HotSpotSet (HotSpotSet 1)
		Set<HotSpot> hotSpots1 = new HashSet<HotSpot>();
		hotSpots1.add(hotSpot1);
		
		//Product 1 Image 1 HotSpot 1 KeywordSet (KeywordSet 1)
		Set<KeywordList> keywordsList1 = new HashSet<KeywordList>();
		keywordsList1.add(key1);
		keywordsList1.add(key2);

		hotSpot1.setKeywords(keywordsList1);
		img1.setHotSpots(hotSpots1);
		prod1.setImages(imgSet1);
		
		//******************************************************************//

		//Product 2
		Product prod2 = new Product();
		prod2.setDescription("Mac Book");
		prod2.setProductName("Mac Book Pro");

		// Product 2 Image 1 (Image 2)
		Image img2 = new Image();
		img2.setImageURL("http://icdn2.digitaltrends.com/image/macbook-air-pictures-640x426-c.jpg");
		img2.setHeight(426);
		img2.setWidth(640);
		
		// Product 2 Image 2 (Image 3)
		Image img3 = new Image();
		img3.setImageURL("http://www9.pcmag.com/media/images/134101-apple-macbook-13-inch-angle.jpg");
		img3.setHeight(356);
		img3.setWidth(400);
		
		// Product 2 Image 2 HotSpot (HotSpot 2)
		HotSpot hotSpot2 = new HotSpot();
		hotSpot2.setTop(150);
		hotSpot2.setLeft(250);
		
		// Product 2 Image 3 HotSpot (HotSpot 3)
		HotSpot hotSpot3 = new HotSpot();
		hotSpot3.setTop(100);
		hotSpot3.setLeft(200);

		// Product 2 Image 2 HotSpot 2 KeywordList (Keyword 3)
		KeywordList key3 = new KeywordList();
		key3.setKeywordGroup("Manufacturer");
		key3.setDescription("Apple");
		key3.setRanking(3);
		
		// Product 2 Image 2 HotSpot 2 KeywordList (Keyword 4)
		KeywordList key4 = new KeywordList();
		key4.setKeywordGroup("OS");
		key4.setDescription("Mac");
		key4.setRanking(1);

		// Product 2 Image 2 HotSpotSet (HotSpotSet 2)
		HashSet<HotSpot> hotSpots2 = new HashSet<HotSpot>();
		hotSpots2.add(hotSpot2);
		
		//Product 2 Image 3 HotSpotSet (HotSpotSet 3)
		HashSet<HotSpot> hotSpots3 = new HashSet<HotSpot>();
		hotSpots3.add(hotSpot3);
		
		//Product 2 Image 2 HotSpot 2 KeywordSet (KeywordSet 2)
		HashSet<KeywordList> keywordsList2 = new HashSet<KeywordList>();
		keywordsList2.add(key3);
		keywordsList2.add(key4);
		
		//Product 2 Image 3 HotSpot 3 KeywordSet (KeywordSet 3)
		HashSet<KeywordList> keywordsList3 = new HashSet<KeywordList>();
		keywordsList3.add(key3);
		keywordsList3.add(key4);
		
		hotSpot2.setKeywords(keywordsList2);
		hotSpot3.setKeywords(keywordsList3);
		img2.setHotSpots(hotSpots2);
		img3.setHotSpots(hotSpots3);

		HashSet<Image> imgSet2 = new HashSet<Image>();
		imgSet2.add(img2);
		imgSet2.add(img3);
		prod2.setImages(imgSet2);

		productDao.save(prod1);
		productDao.save(prod2);

		return "test1";
	}
	
	/**
	 * Handles request to reads Product detail using Product ID and Add to Model data.
	 * @param id Product ID to be searched.
	 * @param model Model Object to add attributes to request.
	 * @return JSP View name as String
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public String read(@RequestParam("id") int id, Model model) {
		//Product
		Product product=productDao.getProduct(id);
		model.addAttribute("Product", product);
		return "test1";
	}
	
	/**
	 * Handles request to return Hibernate Cache Statistics
	 * @param model Model Object to add attributes to request.
	 * @return JSP View name as String
	 * @throws Exception thrown HibernateStatisticsFactoryBean if it is unable to create Hibernate SessionFactory
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hibernateStatistics", method = RequestMethod.GET)
	public String getHibernateStatistics(Model model) throws Exception {
		JSONObject queryCacheStats = hibernateStatisticsFactoryBean.getQueryStatistics("from KeywordList k order by k.keywordGroup, k.description");
		JSONObject secondLevelCacheStats = hibernateStatisticsFactoryBean.getSecondLevelCacheStatistics("product2Cache");
		JSONObject hibernateCacheStats = new JSONObject();
		hibernateCacheStats.put("Query Cache Statistics", queryCacheStats);
		hibernateCacheStats.put("Second Level Cache Statistics", secondLevelCacheStats);
		model.addAttribute("Statistics", hibernateCacheStats);
		return "test1";
	}
	
	

}
