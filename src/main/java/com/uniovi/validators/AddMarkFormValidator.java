package com.uniovi.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.uniovi.entities.Mark;

@Component
public class AddMarkFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> aClass) {
		return Mark.class.equals(aClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Mark mark = (Mark) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "Error.empty");
		if (mark.getScore() < 0 || mark.getScore() > 10) {
			errors.rejectValue("score", "Error.addMark.score.value");
		}
	}

}
