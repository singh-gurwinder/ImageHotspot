package org.sidhu.utility.imagehotspot.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.sidhu.utility.imagehotspot.dao.HotspotDao;
import org.sidhu.utility.imagehotspot.dao.ImageDao;
import org.sidhu.utility.imagehotspot.dao.KeywordDao;
import org.sidhu.utility.imagehotspot.dao.ProductDao;
import org.sidhu.utility.imagehotspot.entity.HotSpot;
import org.sidhu.utility.imagehotspot.entity.Image;
import org.sidhu.utility.imagehotspot.entity.KeywordList;
import org.sidhu.utility.imagehotspot.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

@Service
@Path("/")
public class ImageHotspotService implements ServletContextAware {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ImageDao imageDao;
	
	@Autowired
	private HotspotDao hotspotDao;
	
	@Autowired
	private KeywordDao keywordDao;

	private ServletContext servletContext;

	Log log = LogFactory.getLog(ImageHotspotService.class);

	

	public ImageHotspotService() {
		// No Arguments Constructor
	}

	/**
	 * Handles REST service request for Product object whose ID is provided as Request Parameter. 
	 * @param id ID of Product to search.
	 * @return Product as JSONObject.
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("service/{id}")
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProductById(@PathParam("id") String id) {
		JSONObject obj = new JSONObject();
		Product prod = null;
		if (id != null) {
			try {
				int productId = Integer.parseInt(id);
				prod = productDao.getProduct(productId);
			} catch (NumberFormatException ex) {
				log.error(ex.getClass() + ": Product ID must be int value.");
				ex.printStackTrace();
			}
		} else {
			obj.put("Error", "ID must be provided");
		}
		if (prod != null) {
			obj.put("Product", prod.toJSON());
			String path = servletContext.getContextPath() + servletContext.getInitParameter("hotspotImage");
			obj.put("HotspotImage", path);
		} else {
			obj.put("Error", "Product not found");
		}
		return Response.status(200).entity(obj.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Handles REST service request to return List of all Products.
	 * @return Products List as JSONObject.
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("service")
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllProducts() {
		JSONObject obj = new JSONObject();
		List<Product> productList = productDao.getProductsList();
		obj.put("Result", new JSONArray());
		for (Product prod : productList) {
			JSONArray result = (JSONArray) obj.get("Result");
			JSONObject tempObj = new JSONObject();
			tempObj.put("Product", prod.toJSON());
			result.add(tempObj);
		}
		return Response.status(200).entity(obj.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Handles REST service request to search for all Product which have particular Keywords.
	 * @param keywords String list of Keywords to search.
	 * @return Products List as JSONObject.
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/service/ajax/search")
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchProducts(@QueryParam("keywords") String keywords) {
		JSONObject obj = new JSONObject();
		List<String> keyList = new ArrayList<String>(Arrays.asList(keywords.split(",")));
		List<Product> productList = productDao.searchProducts(keyList);
		obj.put("Result", new JSONArray());
		for (Product prod : productList) {
			JSONArray result = (JSONArray) obj.get("Result");
			JSONObject tempObj = new JSONObject();
			tempObj.put("Product", prod.toJSON());
			result.add(tempObj);
		}
		return Response.status(200).entity(obj.toString()).header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Handles AJAX request to search for a Product using Product ID.
	 * @param productId ID of Product to search.
	 * @return Product and All Keywords as JSONObject.
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/service/getProductAjax")
	@Consumes(MediaType.TEXT_HTML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response productHotspotModel(@QueryParam("productid") Integer productId) {
		JSONObject obj = new JSONObject();
		obj.put("Product", productDao.getProduct(productId).toJSON());
		obj.put("Keywords", new JSONArray());
		List<KeywordList> keywords = keywordDao.getKeywordsList();
		for (KeywordList keyword : keywords) {
			JSONArray result = (JSONArray) obj.get("Keywords");
			JSONObject tempObj = new JSONObject();
			tempObj.put("Keyword", keyword.toJSON());
			result.add(tempObj);
		}
		return Response.status(200).entity(obj.toString()).header("Access-Control-Allow-Origin", "*").build();
	}
	
	/**
	 * Handle AJAX request to Update Images and Hotspots for a Product.
	 * @param productStr String representation of Product JSONobject
	 * @return Product update result as String.
	 * @throws ParseException If JSONParser throws Exception while parsing productStr.
	 */
	@POST
	@Path("/service/updateProduct")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public Response updateProduct(final @FormParam("product") String productStr) throws ParseException{
		JSONParser parser = new JSONParser();
		JSONObject jsonProduct = null;
		try {
			jsonProduct = (JSONObject) parser.parse(productStr);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		Product product = productDao.getProduct(Integer.parseInt(jsonProduct.get("ID").toString()));
		updateProduct(product,jsonProduct);
		return Response.status(200).entity("Product Updated").header("Access-Control-Allow-Origin", "*").build();
	}

	/**
	 * Handles request to update existing Product in database.
	 * @param product Product Object to be updated.
	 * @param jsonProduct JSONOBject Product received in HTTP request.
	 */
	private void updateProduct(Product product, JSONObject jsonProduct) {
		
		product.getImages().clear();
		
		JSONArray jsonImages = (JSONArray) jsonProduct.get("Images");
		Iterator<?> jsonImgIterator = jsonImages.iterator();
		while (jsonImgIterator.hasNext()) {
			JSONObject jsonImage = (JSONObject) jsonImgIterator.next();
			
			Image img;
			if (!jsonImage.get("ID").toString().startsWith("Temp")) {
				img = imageDao.getImage(Integer.parseInt(jsonImage.get("ID").toString()));
			} else {
				img = new Image();
				imageDao.save(img);
			}
			
			img.setImageURL(jsonImage.get("URL").toString());
			img.setHeight(Integer.parseInt(jsonImage.get("Height").toString()));
			img.setWidth(Integer.parseInt(jsonImage.get("Width").toString()));
			
			updateImage(img,jsonImage);
			product.getImages().add(img);
		}
		
		productDao.updateProductImages(product);
		
	}

	/**
	 * Updates Image for a Product in database.
	 * @param img Image Object to be updated.
	 * @param jsonImage JSONObject representation of Image received as HTTP request.
	 */
	private void updateImage(Image img, JSONObject jsonImage) {
		
		img.getHotSpots().clear();
		
		JSONArray hotspots = (JSONArray) jsonImage.get("Hotspots");
		Iterator<?> hotspotIterator = hotspots.iterator();
		while (hotspotIterator.hasNext()) {
			JSONObject jsonHotspot = (JSONObject) hotspotIterator.next();
			HotSpot hotspot;
			if (!jsonHotspot.get("ID").toString().startsWith("Temp")) {
				hotspot = hotspotDao.getHotspot(Integer.parseInt(jsonHotspot.get("ID").toString()));
			} else {
				hotspot = new HotSpot();
				hotspotDao.save(hotspot);
			}

			hotspot.setLeft(Integer.parseInt(jsonHotspot.get("Left").toString()));
			hotspot.setTop(Integer.parseInt(jsonHotspot.get("Top").toString()));
			
			updateHotspot(hotspot, jsonHotspot);
			img.getHotSpots().add(hotspot);
		}
		
		imageDao.update(img);
	}

	/**
	 * Updates Hotspot for an Image in database.
	 * @param hotspot HotSpot object to be updated.
	 * @param jsonHotspot JSONObject representation of HotSpot received as HTTP request.
	 */
	private void updateHotspot(HotSpot hotspot, JSONObject jsonHotspot) {
		
		hotspot.getKeywords().clear();
		
		JSONArray keywords = (JSONArray) jsonHotspot.get("Keywords");
		Iterator<?> keywordsIterator = keywords.iterator();
		while (keywordsIterator.hasNext()) {
			JSONObject jsonKeyword = (JSONObject) keywordsIterator.next();
			KeywordList keyword = keywordDao.getKeyword(Integer.parseInt(jsonKeyword.get("ID").toString()));
			hotspot.getKeywords().add(keyword);
		}
		
		hotspotDao.update(hotspot);
		
	}

	/**
	 * Setter function to set servletContext as this Service Class implements ServletContextAware.
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
