package com.company.utils;

import com.company.exceptions.ValidationException;

public class ValidationUtils {
    public static void validateNotBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("Value is empty");
        }
    }

    public static void validatePositiveAmount(double amount) {
        if (amount <= 0) {
            throw new ValidationException("Amount must be greater than 0");
        }
    }

    public static void validateEmailLike(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email is empty");
        }
        String trimmed = email.trim();
        if (!trimmed.contains("@") || !trimmed.contains(".")) {
            throw new ValidationException("Email is invalid");
        }
    }
}
