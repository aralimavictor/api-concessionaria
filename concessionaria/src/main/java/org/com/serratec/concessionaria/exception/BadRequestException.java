package org.com.serratec.concessionaria.exception;

public class BadRequestException extends RuntimeException
{
    public BadRequestException(String message){
        super(message);
    }
}
