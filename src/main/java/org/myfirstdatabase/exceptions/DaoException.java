package org.myfirstdatabase.exceptions;

public class DaoException extends RuntimeException {
    public DaoException(Throwable e) {
        super(e);
    }
}