package sdre.annotations;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidRangeImpl implements ConstraintValidator<ValidRange, Integer>{

	@Override
	public void initialize(ValidRange constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return false;
	}

}
