package com.example.mycommunity.annotation.annotationImp;

import com.example.mycommunity.annotation.TagCheck;
import com.example.mycommunity.pojo.entity.Tag;
import com.example.mycommunity.utils.TopicConstants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TagCheckImp implements ConstraintValidator<TagCheck, Tag[]> {
    private int min;
    private int max;

    @Override
    public void initialize(TagCheck constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Tag[] tags, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;
        if (tags.length >= this.min && tags.length <= this.max) {
            for (Tag tag : tags) {
                int length = tag.getName().length();
                if (length < TopicConstants.TAG_NAME_MIN_LENGTH || length > TopicConstants.TAG_NAME_MAX_LENGTH) {
                    isValid = false;
                    break;
                }
            }
        } else
            isValid = false;
        return isValid;
    }
}
