package com.project.flightManagement.DTO.ValidNamSanXuat;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = NamSanXuatValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidNamSanXuat {
    String message() default "Năm sản xuất không được vượt quá năm hiện tại";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
