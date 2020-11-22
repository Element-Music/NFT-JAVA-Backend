package com.Element.Music.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaternUtil {

    //手机号正则
    public static boolean isMobile(String mobile) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3])|(17[5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
    // 用户名正则: 大写字母+小写字母+数字+下划线
    public static boolean isUserName(String UserName) {
        String regex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[_]).{8,}$";
        Pattern p = Pattern.compile ( regex, Pattern.CASE_INSENSITIVE );
        Matcher m = p.matcher ( UserName );
        return m.matches ();
    }
}
