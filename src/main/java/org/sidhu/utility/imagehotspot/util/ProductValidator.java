package org.sidhu.utility.imagehotspot.util;

import javax.servlet.http.HttpServletRequest;

import org.sidhu.utility.imagehotspot.dao.ProductDao;
import org.sidhu.utility.imagehotspot.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("productValidator")
public class ProductValidator implements Validator{
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private HttpServletRequest request;
	
	@Override
	public boolean supports(Class<?> paramClass) {
		return Product.class.equals(paramClass);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		Product product = (Product) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "productName", "product.productName.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "product.description.required");
		if(request.getParameter("formType").equals("add") && product.getProductName().trim()!="" && productDao.productExists(product.getProductName()))
			errors.rejectValue("productName", "product.duplicate");
	}

}
