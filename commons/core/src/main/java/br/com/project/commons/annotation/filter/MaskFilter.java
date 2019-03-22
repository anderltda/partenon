package br.com.project.commons.annotation.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface MaskFilter {
	String label();
	String mask();
	String style() default "";
	String icon() default "";
	int length();
	boolean exclusiveDialog() default false;
	boolean exclusiveForm() default false;
	boolean required() default false;
}
