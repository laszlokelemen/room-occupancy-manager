package com.example.roomoccupancymanager.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ExceptionDetails {
    private Date timestamp;
    private List<String> messages;
    private String path;
}