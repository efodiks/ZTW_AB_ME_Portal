package com.abme.portal.rest.exceptions;


public class EmailAlreadyUsedException extends BadRequest {
    public EmailAlreadyUsedException() {
        super("Email is already in use!");
    }
}
