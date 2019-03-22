package br.com.project.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface InputText {
	String label();
	String style() default "";
	String styleClass() default "";
	String onkeyup() default "";
	int length();
	boolean required() default false;
}
