package com.devsuperior.client.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.client.services.exceptions.DatabaseException;
import com.devsuperior.client.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError standardError = new StandardError();
		standardError.setTimestamp(Instant.now());
		standardError.setError("Entity not found");
		standardError.setStatus(status.value());
		standardError.setMessage(e.getMessage());
		standardError.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(standardError);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> entityNotFound(DatabaseException e, HttpServletRequest request){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		StandardError standardError = new StandardError();
		standardError.setTimestamp(Instant.now());
		standardError.setError("Database exception");
		standardError.setStatus(status.value());
		standardError.setMessage(e.getMessage());
		standardError.setPath(request.getRequestURI());
		return ResponseEntity.status(status).body(standardError);
	}
}
