package com.example.phamngocan.testmediaapp.indicator;

public class PageException extends Exception {
    @Override
    public String getMessage() {
        return "Page must larger 1";
    }
}
