package com.videostream.helper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CustomMessage {

  
    private String message;
    private boolean success = false;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public CustomMessage(String message, boolean success) {
	super();
	this.message = message;
	this.success = success;
    }
    public CustomMessage() {
	super();
	
    }
    
    
}
