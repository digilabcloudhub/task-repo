package com.neueda.atm.task.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	
	
@ExceptionHandler(ResourceNotAutenticateException.class)
@ResponseStatus(value=HttpStatus.FORBIDDEN)
public @ResponseBody ExceptionResponse	handleResourceNotFound(ResourceNotAutenticateException exception,HttpServletRequest request)
{
	ExceptionResponse response=new ExceptionResponse();
	response.setMessage(exception.getMessage());
	response.setRequestURI(request.getRequestURI());
	return response;
		
}

@ExceptionHandler(Exception.class)
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public @ResponseBody ExceptionResponse	handleException(Exception exception,HttpServletRequest request)
{
	ExceptionResponse response=new ExceptionResponse();
	response.setMessage(exception.getMessage());
	response.setRequestURI(request.getRequestURI());
	return response;
		
	}

@ExceptionHandler(InsufficientBalanceException.class)
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public @ResponseBody ExceptionResponse	handleInsufficientBalance(InsufficientBalanceException exception,HttpServletRequest request)
{
	ExceptionResponse response=new ExceptionResponse();
	response.setMessage(exception.getMessage());
	response.setRequestURI(request.getRequestURI());
	return response;
		
}


}
