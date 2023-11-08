package com.example.roomoccupancymanager.exception;

import java.util.Date;
import java.util.List;

public record ExceptionDetails(Date timestamp, List<String> messages, String path) {
}