package io.github.edsonisaac.beemonitor.exceptions;

import lombok.Builder;

/**
 * The type Standard error.
 *
 * @author Edson Isaac
 */
@Builder
public class StandardError {
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}