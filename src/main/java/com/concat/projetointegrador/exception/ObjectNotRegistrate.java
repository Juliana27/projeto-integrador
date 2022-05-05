package com.concat.projetointegrador.exception;

public class ObjectNotRegistrate extends RuntimeException {

    private static final long serialVersionUID = 1234L;

    public ObjectNotRegistrate() {
    }

    public ObjectNotRegistrate(String message) {
        super(message);
    }

    public ObjectNotRegistrate(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotRegistrate(Throwable cause) {
        super(cause);
    }

    public ObjectNotRegistrate(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
