package com.stefan.util;

public class CustomValidation {

    public static <T> T setIfNotNull(T oldProperty, T newProperty) {
        return newProperty == null ? oldProperty : newProperty;
    }
}
