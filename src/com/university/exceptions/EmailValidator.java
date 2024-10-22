package com.university.exceptions;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final String EMAIL_REGEX = "^[up]\\d{2}[a-zA-Z]+\\d{3}@coed\\.svnit\\.ac\\.in$";
    public boolean validateEmail(String email) throws InvalidEmailException{
        if(!Pattern.matches(EMAIL_REGEX,email)){
            throw new InvalidEmailException("Email not correct. Please try again after sometime");
        }else{
            return false;
        }
    }
}