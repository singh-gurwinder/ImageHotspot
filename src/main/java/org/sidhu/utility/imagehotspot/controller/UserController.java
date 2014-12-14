package org.sidhu.utility.imagehotspot.controller;

import java.util.HashSet;
import java.util.Set;

import org.sidhu.utility.imagehotspot.dao.UserDao;
import org.sidhu.utility.imagehotspot.entity.User;
import org.sidhu.utility.imagehotspot.entity.UserRole;
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

@Controller
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	@Qualifier("userValidator")
	private Validator userValidator;
	 /**
	  * Sets Validator for data validation for Add new User Form.
	  * @param binder WebDataBinder Object.
	  */
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(userValidator);
	}

	/**
	 * Create Model data for Register New User Form.
	 * @return User Object.
	 */
	@ModelAttribute("user")
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public User createProductModel() {
		return new User();
	}
	
	/**
	 * Handles request to persist new User to database.
	 * @param user User Object to be saved.
	 * @param bindingResult  BindingResult Object representing if any error occurred while validation.
	 * @param model Model Object to add attributes to request.
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void saveUser(@ModelAttribute("user") @Validated User user,
			BindingResult bindingResult,  Model model) {
		if (!bindingResult.hasErrors()) {
			user.setEnabled(true);
			UserRole userRole=new UserRole(user, "ROLE_ADMIN");
			Set<UserRole> userRoleSet = new HashSet<UserRole>();
			userRoleSet.add(userRole);
			user.setUserRole(userRoleSet);
			userDao.save(user);
			model.addAttribute("result", "success");
		}
	}
}
