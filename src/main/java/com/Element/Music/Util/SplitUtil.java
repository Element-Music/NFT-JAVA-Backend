package com.Element.Music.Util;

import com.Element.Music.Emun.MusicType;

import java.util.LinkedList;
import java.util.List;

public class SplitUtil {
    public static List<MusicType> musicTypeSplit(String musicTypeString) {
        if (musicTypeString == null || musicTypeString.length() == 0)
            return new LinkedList<>();
        String[] musicTypes = musicTypeString.split("/");
        List<MusicType> musicTypeList = new LinkedList<>();
        for (String s : musicTypes) {
            musicTypeList.add(MusicType.valueOf(s));
        }
        return musicTypeList;
    }
}
