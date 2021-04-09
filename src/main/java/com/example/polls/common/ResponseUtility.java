package com.example.polls.common;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtility implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static <T> ResponseEntity<CommonResponse<T>> generateResponse(T data,Boolean success,String message,HttpStatus status)
	{
		CommonResponseHeader commonResHeader;
		commonResHeader=new CommonResponseHeader(success, message);
		
		return new ResponseEntity<CommonResponse<T>>(new CommonResponse<T>(commonResHeader, data), status);
	}
}
