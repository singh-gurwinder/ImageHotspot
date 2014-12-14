package org.sidhu.utility.imagehotspot.controller;

import org.sidhu.utility.imagehotspot.dao.ProductDao;
import org.sidhu.utility.imagehotspot.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
public class ProductController {
	@Autowired
	private ProductDao productDao;

	@Autowired
	@Qualifier("productValidator")
	private Validator productValidator;

	/**
	 * Sets Product Validator for data validation while creating/modifying a Product in Admin Console.
	 * @param binder WebDataBinder Object.
	 */
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(productValidator);
	}

	/**
	 * Handles request to provide List of All Products to be used in Admin Console.
	 * @param model Model Object to add attributes to request.
	 */
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public void productsMainHome(Model model) {
		model.addAttribute("productList", productDao.getProductsList());
	}

	/**
	 * Handles request to delete a Product.
	 * @param model Model Object to add attributes to request.
	 * @param productId ID of Product to be deleted.
	 */
	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public void productsHome(Model model, @RequestParam(value = "deleteproduct", required = false) Integer productId) {
		if (productId != null) {
			productDao.delete(productId);
			model.addAttribute("id", productId);
		}
		model.addAttribute("productList", productDao.getProductsList());
	}

	/**
	 * Create Model data for Add New Product Form.
	 * @return Product Object.
	 */
	@ModelAttribute("product")
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public Product createProductModel() {
		return new Product();
	}

	/**
	 * Handles request to persist a new Product to database.
	 * @param product ID of Product to be added.
	 * @param bindingResult BindingResult Object representing if any error occurred while validation.
	 * @param model Model Object to add attributes to request.
	 */
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public void saveProduct(@ModelAttribute("product") @Validated Product product,
			BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			productDao.save(product);
			model.addAttribute("result", "success");
		}
	}

	/**
	 * Create Model data for Edit Product Form.
	 * @param productId ID of Product to be used as Model.
	 * @return Product Object.
	 */
	@ModelAttribute("product")
	@RequestMapping(value = "/modifyProduct", method = RequestMethod.GET)
	public Product modifyProductModel(@RequestParam("productid") Integer productId) {
		return productDao.getProduct(productId);
	}

	/**
	 * Handles request to Modify an existing Product.
	 * @param product ID of Product to be modified.
	 * @param bindingResult BindingResult Object representing if any error occurred while validation.
	 * @param model Model Object to add attributes to request.
	 */
	@RequestMapping(value = "/modifyProduct", method = RequestMethod.POST)
	public void modifyProduct(@ModelAttribute("product") @Validated Product product,
			BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			productDao.update(product);
			model.addAttribute("result", "success");
		}
	}

}
