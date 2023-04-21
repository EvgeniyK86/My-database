package org.myfirstdatabase.validator;

import lombok.Value;

@Value(staticConstructor = "of")
public class Error {
    String code;
    String message;

//    public static Error of(String code, String message) {
//        return new Error(code, message);
//    }
}