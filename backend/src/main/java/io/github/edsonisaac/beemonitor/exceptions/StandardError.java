package io.github.edsonisaac.beemonitor.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a standard error response.
 * Contains information about an error, such as timestamp, status, error message, and path.
 * This class is used for error handling and response in case of exceptions.
 *
 * @author Edson Isaac
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardError {
    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}