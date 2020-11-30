package com.Element.Music.Exception;

public class SongException extends ElementException {

    public SongException() {
        super("Song存在异常");
    }

    public SongException(String s) {
        super(s);
    }
}
