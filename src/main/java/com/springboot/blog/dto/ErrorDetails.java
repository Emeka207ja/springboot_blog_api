package com.springboot.blog.dto;

public class ErrorDetails {
    private final String timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(String timestamp,String message,String details){
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
