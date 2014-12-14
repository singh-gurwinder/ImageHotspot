package org.sidhu.utility.imagehotspot.controller;

import org.sidhu.utility.imagehotspot.dao.KeywordDao;
import org.sidhu.utility.imagehotspot.entity.KeywordList;
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
public class KeywordController {

	@Autowired
	private KeywordDao keywordDao;
	
	@Autowired
	@Qualifier("keywordValidator")
	private Validator keywordValidator;

	/**
	 * Sets Keyword Validator which is used for Form Validation while creating new Keywords.
	 * @param binder WebDataBinder Object.
	 */
	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(keywordValidator);
	}

	/**
	 * Creates Model for Add New Keyword Form.
	 * @return KeywordList Model.
	 */
	@ModelAttribute("keyword")
	@RequestMapping(value = "/addKeyword", method = RequestMethod.GET)
	public KeywordList createKeywordModel() {
		return new KeywordList();
	}

	/**
	 * Handles request to persist new Keyword to database.
	 * @param keyword KeywordList Object.
	 * @param bindingResult BindingResult Object representing if any error occurred while validation.
	 * @param model Model Object to add attributes to request.
	 */
	@RequestMapping(value = "/addKeyword", method = RequestMethod.POST)
	public void saveKeyword(@ModelAttribute("keyword") @Validated KeywordList keyword,
			BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			keywordDao.save(keyword);
			model.addAttribute("result", "success");
		}
	}

	/**
	 * Handles request to provide Keywords List to be displayed on Keywords Tab of Admin Console.
	 * @param model Model Object to add attributes to request.
	 */
	@RequestMapping(value = "/keyword", method = RequestMethod.GET)
	public void keywordsHome(Model model) {
		model.addAttribute("keywordList", keywordDao.getKeywordsList());
	}

	/**
	 * Handles request to delete a Keyword.
	 * @param model Model Object to add attributes to request.
	 * @param keywordId ID of Keyword to be deleted.
	 */
	@RequestMapping(value = "/editKeyword", method = RequestMethod.GET)
	public void editKeyword(Model model, @RequestParam(value = "deletekeyword", required = false) Integer keywordId) {
		if (keywordId != null) {
			keywordDao.delete(keywordId);
			model.addAttribute("id", keywordId);
		}
		model.addAttribute("keywordList", keywordDao.getKeywordsList());
	}

	/**
	 * Create Model data for Keyword Editing Form.
	 * @param keywordId ID of Keyword to be modified.
	 * @return Keyword KeywordList object as Model data.
	 */
	@ModelAttribute("keyword")
	@RequestMapping(value = "/modifyKeyword", method = RequestMethod.GET)
	public KeywordList modifyKeywordModel(@RequestParam("keywordid") Integer keywordId) {
		return keywordDao.getKeyword(keywordId);
	}

	/**
	 * Updates existing Keyword in database.
	 * @param keyword KeywordList Object to be updated.
	 * @param bindingResult BindingResult Object representing if any error occurred while validation.
	 * @param model Model Object to add attributes to request.
	 */
	@RequestMapping(value = "/modifyKeyword", method = RequestMethod.POST)
	public void modifyKeyword(@ModelAttribute("keyword") @Validated KeywordList keyword,
			BindingResult bindingResult, Model model) {
		if (!bindingResult.hasErrors()) {
			keywordDao.update(keyword);
			model.addAttribute("result", "success");
		}
	}
	
}
