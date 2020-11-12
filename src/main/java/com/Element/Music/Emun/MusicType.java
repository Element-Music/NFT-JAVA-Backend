package com.Element.Music.Emun;

public enum MusicType {

    RAP(0, "Rap"),
    JAZZ(1, "Jazz");

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int code;
    private String name;

    private MusicType(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
