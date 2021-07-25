package com.Element.Music.Util;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class EncryptUtil {
    //加密算法，输入字符串返回字符串
    public static String encrypt(String string) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] textByte = new byte[0];
        String encodedText = "";
        try {
            //获取原字符串base64编码的值
            textByte = string.getBytes("UTF-8");
            encodedText = encoder.encodeToString(textByte);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //用base64编码后通过再用md5加密
        return DigestUtils.md5DigestAsHex(encodedText.getBytes());
    }
}

//6856007488780b2cb3413ad53cb53c1a
