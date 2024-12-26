package com.example.quotivation.dto.log.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class LogRequest {

    private String ip;
    private String uri;
    private int status;
    private String method;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> parameters;
}
