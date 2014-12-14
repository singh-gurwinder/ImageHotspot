package org.sidhu.utility.imagehotspot.util;

import javax.servlet.http.HttpServletRequest;

import org.sidhu.utility.imagehotspot.dao.UserDao;
import org.sidhu.utility.imagehotspot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("userValidator")
public class UserValidator implements Validator{
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private UserDao userDao;

	@Override
	public boolean supports(Class<?> paramClass) {
		return User.class.equals(paramClass);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		User user = (User) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.username.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.required");
		if(request.getParameter("formType").equals("add") && user.getUsername().trim()!="" && userDao.userExists(user.getUsername()))
			errors.rejectValue("username", "username.duplicate");
		
	}

}
