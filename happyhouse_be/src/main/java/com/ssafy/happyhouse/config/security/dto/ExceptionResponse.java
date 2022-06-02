package com.ssafy.happyhouse.config.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
	private String message;

    private Map<String, String> attributes;

    public static ExceptionResponse from(String message) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.message = message;
        return exceptionResponse;
    }

	public static ExceptionResponse from(String message, Map<String, String> errors) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.message = message;
        exceptionResponse.attributes = errors;
        return exceptionResponse;
    }
	

    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}
