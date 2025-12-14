package br.edu.ifpb.entity.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EqualPasswordsValidator.class)
public @interface EqualPasswords {
	String message() default "{senhas.diferentes}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String campoSenha();
    String campoSenha2();
}