package com.zcl.AESCBC;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 目标：学会使用AESCBC加密和解密
 */
public class Test {

    public static final int IV_LENGTH_16 = 16;
    public static final int KEY_LENGTH_16 = 16;
    public static final int key_LENGTH_32 = 32;
    public static final String AEC_CBC_NO_PADDING = "AES/CBC/NoPadding";
    public static final String AES = "AES";

    public static void main(String[] args) throws Exception {
        String key = "1111222233334444";
        String iv = "5555666677778888";
        String content = "Hello Worasdlihjasliklllklklklklkllllllllllllllkkkld";
        String encryet = encryptByCBC(key, iv, content);
        System.out.println(encryet); // [B@7e774085

        // decryptByECB(key, iv, encryet);
    }


    /**
     * AES CBC 模式加密
     * @param key 加密的key
     * @param iv 偏移矢量
     * @param content 待解密的内容
     * @return 加密之后的字符串
     */
    public static String encryptByCBC(String key,String iv,String content) throws Exception {
        checkKey(key);
        checkIV(iv);

        // 调用类加密
            Cipher cipger = Cipher.getInstance(AEC_CBC_NO_PADDING); // 后面声明是一个固定的写法
        int blockSize = cipger.getBlockSize();
        // content必须是整数被
        int contlegth = content.getBytes().length;
        // 判断是不整数倍
        if(contlegth % blockSize != 0){
            contlegth = contlegth + (blockSize - (contlegth % blockSize));
        }
        // 重新生成一个数组
        byte[] newBytes = new byte[contlegth];
        // 将原数组的内容拷贝一次到新的数组直中
        System.arraycopy(content.getBytes(),0,newBytes,0,content.getBytes().length);

        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), AES);
        // 接受偏移矢量的字节
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // 选择模式
        cipger.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
        byte[] bytes = cipger.doFinal(newBytes);
        // jdk8之后的版本使用getEncode
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
        return encoder.encode(bytes).toString();
    }

    /**
     * 检查加密的key
     * @param key 加密的密钥
     */
    private static void checkKey(String key) throws Exception {
        // 按Ctrl+Alt+C建将16改成一个常量
        if(key == null || key.length() != KEY_LENGTH_16 && key.length() != key_LENGTH_32){
            throw new Exception("加密密钥不正确");
        }
    }

    /**
     * 检查偏移适量
     * @param iv 偏移矢量
     */
    private static void checkIV(String iv) throws Exception {
        // 按Ctrl+Alt+C建将16改成一个常量
        if(iv == null || iv.length() != IV_LENGTH_16){
            throw new Exception("偏移矢量错误");
        }
    }

    /**
     * AES CBC模式解密
     * @param key 加密的key
     * @param iv 偏移矢量
     * @param encrypt 加密后的内容
     */
    public void decrptByCBC(String key,String iv,String encrypt) throws Exception {
        checkKey(key);
        checkIV(iv);

        SecretKeySpec secretKeySpec = new SecretKeySpec(encrypt.getBytes(),AES);
        // 加密模式
        Cipher cipher = Cipher.getInstance(AEC_CBC_NO_PADDING);
        // 偏移矢量
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        // 设置为解密模式(传入key和偏移矢量)
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec,ivParameterSpec);
        Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
    }

    /**
     * AES ECB模式解密
     *
     * @param key     加密的秘钥
     * @param encrypt 加密后的内容
     * @return 解密后的内容
     * @throws Exception 异常信息
     */
    public static String decryptByECB(String key, String encrypt) throws Exception {
        checkKey(key);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES);
        //AES/ECB/PKCS5Padding 格式为 "算法/模式/补码方式"
        Cipher cipher = Cipher.getInstance(AEC_CBC_NO_PADDING);
        //设置为解密模式，解密的key
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        //base64解密
        /*Base64.Encoder encoder = Base64.getEncoder();
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodeBuffer = decoder.decode(encrypt);
        //aes解密
        byte[] bytes = cipher.doFinal(decodeBuffer);
        return new String(bytes);*/
        return " ";
    }
}
