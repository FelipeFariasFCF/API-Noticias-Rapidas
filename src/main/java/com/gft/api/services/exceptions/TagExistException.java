package com.gft.api.services.exceptions;

import java.io.Serial;

public class TagExistException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public TagExistException(String msg) {
        super(msg);
    }
}
