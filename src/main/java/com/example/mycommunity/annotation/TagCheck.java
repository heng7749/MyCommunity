package com.example.mycommunity.annotation;

import com.example.mycommunity.annotation.annotationImp.TagCheckImp;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( {ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TagCheckImp.class)
public @interface TagCheck {
    String message() default "未知错误";
    int min();
    int max();

    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };;
}
