package com.uba.hbd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceConflictException extends Exception {
	
	public ResourceConflictException(String message){
        super(message);
    }

}
