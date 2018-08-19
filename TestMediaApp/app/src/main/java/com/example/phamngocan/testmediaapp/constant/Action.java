package com.example.phamngocan.testmediaapp.constant;

public enum Action {
    MAIN,
    START_FORE,
    PLAY,
    PAUSE,
    STOP,
    NEXT,
    PREVIOUS,
    UPDATE,
    REPEAT,
    SHUFFLE;

    String name;
    Action(){
        name = this.name();
    }

    public String getName() {
        return name;
    }
}
