package com.cts.EbBillManagement.exception;


import java.util.HashMap;
import java.util.Map;
 
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionalHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> handleInvalidArgument(MethodArgumentNotValidException ex){
		Map<String,String> errormap= new HashMap<>();
		for (FieldError error: ex.getBindingResult().getFieldErrors()) {
			errormap.put(error.getField(),error.getDefaultMessage());
		}
		return errormap;
	}
	@ExceptionHandler(RuntimeException.class) 
	public Map<String,String> handleRuntimeException(RuntimeException ex) 
	{ 
		Map<String,String> errormap= new HashMap<>();
		errormap.put("Error Message", ex.getMessage());
		return errormap;
	}

}
