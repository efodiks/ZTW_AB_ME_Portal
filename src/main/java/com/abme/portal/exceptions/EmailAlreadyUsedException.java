package com.abme.portal.exceptions;


public class EmailAlreadyUsedException extends BadRequest {
    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }
}
