package com.example.phamngocan.testmediaapp.Constant;

public enum Action {
    MAIN,
    START_FORE,
    PLAY,
    PAUSE,
    STOP,
    NEXT,
    PREVIOUS;

    String name;
    Action(){
        name = this.name();
    }

    public String getName() {
        return name;
    }
}
