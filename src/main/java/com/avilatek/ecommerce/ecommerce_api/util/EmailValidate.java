package com.avilatek.ecommerce.ecommerce_api.util;

public class EmailValidate {

    private static final org.apache.commons.validator.routines.EmailValidator validator =
            org.apache.commons.validator.routines.EmailValidator.getInstance();

    public static boolean isValid(String email) {
        return validator.isValid(email);
    }
}