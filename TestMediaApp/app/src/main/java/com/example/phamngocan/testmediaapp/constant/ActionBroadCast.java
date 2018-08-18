package com.example.phamngocan.testmediaapp.constant;

public enum ActionBroadCast {
    CURSEEK();

    private String name;

    ActionBroadCast() {
        this.name = this.name();
    }

    public String getName() {
        return name;
    }
}
