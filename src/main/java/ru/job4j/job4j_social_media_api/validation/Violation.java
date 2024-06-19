package ru.job4j.job4j_social_media_api.validation;

import lombok.Getter;

@Getter
public class Violation {
    private final String fieldName;
    private final String message;

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
