package com.tentelemed.archipel.core.application.command;

/**
 * Created with IntelliJ IDEA.
 * User: Mael
 * Date: 17/01/14
 * Time: 17:04
 */
public class RightException extends RuntimeException {
    public RightException() {
    }

    public RightException(String message) {
        super(message);
    }

    public RightException(String message, Throwable cause) {
        super(message, cause);
    }

    public RightException(Throwable cause) {
        super(cause);
    }

    public RightException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
