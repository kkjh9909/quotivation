package com.example.quotivation.dto.log.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class LogRequest {

    private String ip;
    private String uri;
    private String status;
    private String method;
    private String message;
    private String timestamp;
    private Map<String, String> parameters;
}
