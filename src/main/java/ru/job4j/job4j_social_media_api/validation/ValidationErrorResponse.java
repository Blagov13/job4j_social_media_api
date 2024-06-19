package ru.job4j.job4j_social_media_api.validation;

import lombok.Getter;

import java.util.List;

@Getter
public class ValidationErrorResponse {
    private final List<Violation> violations;

    public ValidationErrorResponse(List<Violation> violations) {
        this.violations = violations;
    }
}
