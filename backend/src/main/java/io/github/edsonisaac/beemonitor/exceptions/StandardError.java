package io.github.edsonisaac.beemonitor.exceptions;

import lombok.*;

@Getter
@Setter
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