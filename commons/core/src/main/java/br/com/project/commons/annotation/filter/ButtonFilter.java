package br.com.project.commons.annotation.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface ButtonFilter {
	String label();	
	int order() default -1;
	String width() default "width: 50px;";
	String event() default "onclick";
	String script() default "";
	String oncomplete() default "";
	String update() default "";
	boolean exclusiveDialog() default false;
	boolean exclusiveForm() default false;
	String actionListener();
}
