package com.gop.exception;

public class RsaException extends Exception
{
    private String message;

    public RsaException(String message)
    {
        super();
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return "Rsa error:" + this.message;
    }

}
