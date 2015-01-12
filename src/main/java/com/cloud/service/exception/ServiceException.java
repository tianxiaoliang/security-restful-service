package com.cloud.service.exception;

import java.io.Serializable;

public class ServiceException extends RuntimeException implements Serializable {
	private static final long serialVersionUID = 7786141544419367058L;
	
	public ServiceException(){
		super();
	}
	
	public ServiceException(String message, Throwable cause){
		super(message, cause);
	}
	public ServiceException(Throwable cause){
		super(cause);
	}
	public ServiceException(String msg){
		super(msg);
	}

}
