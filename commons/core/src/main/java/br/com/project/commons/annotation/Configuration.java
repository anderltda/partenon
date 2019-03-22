package br.com.project.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration{
	String fieldValue() default "id";
	String fieldLabel() default "nome";
	boolean processId() default false;
	Class<?> master() default Object.class;
	Class<?> entityClass() default Object.class;
	String entityManager() default "";
}
