package com.example.phamngocan.testmediaapp.constant;

public enum ActionBroadCast {
    CURSEEK(),
    PLAY(),
    PAUSE(),
    PREV(),
    NEXT(),
    STOP(),
    UPDATE_LIST_SHUFFLE(),
    UPDATE_PLAYLIST();

    private String name;

    ActionBroadCast() {
        this.name = this.name();
    }

    public String getName() {
        return name;
    }
}
