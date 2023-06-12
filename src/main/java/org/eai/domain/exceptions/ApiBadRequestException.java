package org.eai.domain.exceptions;

import java.io.Serial;

public class ApiBadRequestException extends Exception {

    @Serial
    private static final long serialVersionUID = 5070445879948173973L;

    public ApiBadRequestException(String msg) {
        super(msg);
    }
}
