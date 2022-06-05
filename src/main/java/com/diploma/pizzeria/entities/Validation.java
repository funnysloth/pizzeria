package com.diploma.pizzeria.entities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    private static final String EMAIl_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    private static final String NAME_PATTERN = "^[A-Za-zа-яА-ЯіІїЇєЄ\\s\\-]+$";
    private static final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$";
    private static final String TEXT_INPUT_PATTERN = "^[A-Za-zа-яА-ЯіІїЇєЄ\\s,\\-0-9]+$";
    private static final String PHONE_PATTERN = "^(\\+?\\d{1,3})?\\d{9}$";
    Pattern emailPattern = Pattern.compile(EMAIl_PATTERN);
    Pattern namePattern = Pattern.compile(NAME_PATTERN);
    Pattern passwordPattern = Pattern.compile(PASSWORD_PATTERN);
    Pattern textInputPattern = Pattern.compile(TEXT_INPUT_PATTERN);
    Pattern phonePattern = Pattern.compile(PHONE_PATTERN);

    public Validation() {
    }

    public boolean checkEmail(String email){
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkName(String name){
        Matcher matcher = namePattern.matcher(name);
        return matcher.matches();
    }

    public boolean checkPassword(String password){
        Matcher matcher = passwordPattern.matcher(password);
        return matcher.matches();
    }

    public boolean checkTextInput(String text){
        Matcher matcher = textInputPattern.matcher(text);
        return matcher.matches();
    }

    public boolean checkPhone(String phone){
        Matcher matcher = phonePattern.matcher(phone);
        return matcher.matches();
    }
}
