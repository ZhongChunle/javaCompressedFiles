package com.zcl.Base64Demo;

import java.util.Base64;

/**
 * 目标学会使用：Base64加密和解密
 */
public class Test {
    public static void main(String[] args) {

        String helloWord = encode("helloWord");
        System.out.println(helloWord); // aGVsbG9Xb3Jk

        System.out.println(decode(helloWord)); // helloWord
    }

    /**
     * base64加密和解密
     * @param message 需要加密的文件
     * @return 编码之后的内容
     */
    public static String encode(String message){
        // 1、拿到base64编码器
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(message.getBytes());
    }


    /**
     * base64解码
     * @param encodeMessage 解码的数据
     * @return 返回解码的内容
     */
    public static String decode(String encodeMessage){
        // 使用Base64解码方法
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(encodeMessage);
        String s = new String(decode);
        return s;
    }
}
