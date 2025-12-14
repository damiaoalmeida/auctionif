package br.edu.ifpb.entity.validation;

import java.util.Objects;

import org.springframework.beans.BeanUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords, Object> {

	private String campoSenha;
    private String campoSenha2;
    
    @Override
    public void initialize(EqualPasswords constraintAnnotation) {
        this.campoSenha = constraintAnnotation.campoSenha();
        this.campoSenha2 = constraintAnnotation.campoSenha2();
    }

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			//                                             User.class         password
			Object senha = BeanUtils.getPropertyDescriptor(value.getClass(), campoSenha)
                    .getReadMethod().invoke(value);

            Object senha2 = BeanUtils.getPropertyDescriptor(value.getClass(), campoSenha2)
                    .getReadMethod().invoke(value);

            boolean iguais = Objects.equals(senha, senha2);
            
            if (!iguais) {
                context.disableDefaultConstraintViolation();
                context
                    .buildConstraintViolationWithTemplate("{senhas.diferentes}")
                    .addPropertyNode(campoSenha2)
                    .addConstraintViolation();
            }

            return iguais;
		} catch(Exception e) {
			return false;
		}
	}

}
