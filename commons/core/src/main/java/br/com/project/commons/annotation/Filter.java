package br.com.project.commons.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.project.commons.util.Type;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Filter {
	String label();
	boolean showOperators() default false;
	int size() default -1;
	boolean autocomplete() default false;
	String[] list() default {};
	Type type() default Type.TEXT;
}
