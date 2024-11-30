package com.zenveus.the_culinary_academy.util;

import javafx.scene.paint.Paint;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isTextFieldValid(TextFields textField, String text){
        String filed = "";

        switch (textField){
            case ID:
                filed = "^([A-Z][0-9]{3})$";
                break;
            case NAME:
                filed = ".*";
                break;
            case EMAIL:
                filed = "^([A-z])([A-z0-9.]){1,}[@]([A-z0-9]){1,10}[.]([A-z]){2,5}$";
                break;
            case ADDRESS:
                filed = ".*";
                break;
            case CONTACT:
                filed = "^([0-9]){10}$";
                break;
            case NIC:
                filed = "^([0-9]{9}[Vv]|[0-9]{12})$";
                break;
            case SALARY:
                filed = "^([0-9]{1,}[.][0-9]{1,})$";
                break;
            case AGE:
                filed = "^([0-9]){1,2}$";
                break;
            case POSITION:
                filed = ".*";
                break;
            case PASSWORD:
                filed = "^[a-zA-Z0-9]+$";
                break;
            case DESCRIPTION:
                filed = ".*";
                break;
            case QTY:
                filed = "^([0-9]){1,}$";
                break;
            case PRICE:
                filed = "^([0-9]{1,}[.][0-9]{1,})$";
                break;
        }

        Pattern pattern = Pattern.compile(filed);

        if (text != null){
            if (text.trim().isEmpty()){
                return false;
            }
        }else {
            return false;
        }

        Matcher matcher = pattern.matcher(text);

        if (matcher.matches()){
            return true;
        }
        return false;
    }

    public static boolean setTextColor(TextFields location, JFXTextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setFocusColor(Paint.valueOf("Green"));
            textField.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        }else {
            textField.setFocusColor(Paint.valueOf("Red"));
            textField.setUnFocusColor(Paint.valueOf("Red"));

            return false;
        }
    }

    public static boolean setTextColor(TextFields textField, JFXPasswordField passwordTxt) {
        if (Regex.isTextFieldValid(textField, passwordTxt.getText())){
            passwordTxt.setFocusColor(Paint.valueOf("Green"));
            passwordTxt.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        }else {
            passwordTxt.setFocusColor(Paint.valueOf("Red"));
            passwordTxt.setUnFocusColor(Paint.valueOf("Red"));

            return false;
        }
    }

    public static boolean setTextColor(TextFields textField, JFXTextArea contextText) {
        if (Regex.isTextFieldValid(textField, contextText.getText())){
            contextText.setFocusColor(Paint.valueOf("Green"));
            contextText.setUnFocusColor(Paint.valueOf("Green"));
            return true;
        }else {
            contextText.setFocusColor(Paint.valueOf("Red"));
            contextText.setUnFocusColor(Paint.valueOf("Red"));

            return false;
        }
    }
}
