package com.kh.secoms.exception;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MissmatchPasswordException.class)
	public ResponseEntity<?>handleMissmatchPassword(MissmatchPasswordException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	
	@ExceptionHandler(AccessTokenExpiredException.class)
	public ResponseEntity<?> handleExpiredToken(AccessTokenExpiredException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<?> handleInvalidParameter(InvalidParameterException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<String> handleDuplicateUser(DuplicateUserException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleArgumentNotValid(MethodArgumentNotValidException e){
		//List list = e.getBindingResult().getFieldErrors();
		Map<String, String> errors = new HashMap();
		/*
		for(int i = 0; i < list.size(); i++) {
			
			log.info("예외가 발생한 필드명 : {}, 이유 : {}",((FieldError)list.get(i)).getField(),((FieldError)list.get(i)).getDefaultMessage());
			
			errors.put(
					((FieldError)list.get(i)).getField(),
			        ((FieldError)list.get(i)).getDefaultMessage()
			        );
					
		}
	*/
		return ResponseEntity.badRequest().body(errors);
	}
}
