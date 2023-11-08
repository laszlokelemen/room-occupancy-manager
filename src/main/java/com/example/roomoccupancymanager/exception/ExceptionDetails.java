package com.example.roomoccupancymanager.exception;

import java.time.LocalDate;
import java.util.List;

public record ExceptionDetails(LocalDate timestamp, List<String> messages, String path) {
}