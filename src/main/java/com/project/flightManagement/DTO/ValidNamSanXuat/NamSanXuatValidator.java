package com.project.flightManagement.DTO.ValidNamSanXuat;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Year;

public class NamSanXuatValidator implements ConstraintValidator<ValidNamSanXuat, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return false; // Hoặc tùy chỉnh logic của bạn
        }
        int currentYear = Year.now().getValue();
        return value <= currentYear;
    }
}
