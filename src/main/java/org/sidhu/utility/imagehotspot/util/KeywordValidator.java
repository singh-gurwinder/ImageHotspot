package org.sidhu.utility.imagehotspot.util;

import javax.servlet.http.HttpServletRequest;

import org.sidhu.utility.imagehotspot.dao.KeywordDao;
import org.sidhu.utility.imagehotspot.entity.KeywordList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("keywordValidator")
public class KeywordValidator implements Validator{
	@Autowired
	private KeywordDao keywordDao;
	@Autowired
	private HttpServletRequest request;
	@Override
	public boolean supports(Class<?> paramClass) {
		return KeywordList.class.equals(paramClass);
	}
	
	@Override
	public void validate(Object obj, Errors errors) {
		KeywordList keyword = (KeywordList) obj;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "keyword.description.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "keywordGroup", "keyword.keywordGroup.required");
		if(keyword.getRanking()<=0)
			errors.rejectValue("ranking", "keyword.ranking.required");
		if(request.getParameter("formType").equals("add") && keyword.getDescription().trim()!="" && keyword.getKeywordGroup().trim()!="" && keywordDao.keywordExists(keyword.getDescription(), keyword.getKeywordGroup()))
			errors.rejectValue("description", "keyword.duplicate");
	}

}
