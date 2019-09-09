package com.zhangku.qukandian.utils;

import com.zhangku.qukandian.config.Config;
import com.zhangku.qukandian.manager.UserManager;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by yuzuoning on 2017/11/13.
 */

public class AES {
    private static String mm = Config.DATAKEY;

    public static String encryUserId(int userId) {
        return String.valueOf(((userId + 13) * 6 + 1234) * 9 + 765) + randomString();
    }

    public static String encryPostId(int posiId) {
        return String.valueOf(((posiId + 14) * 9 + 156) * 7 + 89) + randomString();
    }

    public static String encryLog(int log) {
        return String.valueOf((log + 56) * 9 + 56 + 56);
    }

    private static String randomString() {
        String str = "";
        for (int i = 0; i < 3; i++) {
            str = str + (char) (Math.random() * 26 + 'a');
        }
        return str;
    }

//    public static String encrypt(String key, String data) throws Exception {
//        try {
//            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//            cipher.init(Cipher.ENCRYPT_MODE, keyspec, new IvParameterSpec(
//                    new byte[cipher.getBlockSize()]));
//            byte[] encrypted = cipher.doFinal(data.getBytes());
//            return Base64.encodeToString(encrypted, Base64.DEFAULT);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

//    public static String decrypt(String key, String data) throws Exception {
//        byte[] encrypted1 = Base64.decode(data.getBytes(), Base64.DEFAULT);
//        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//        SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
//        cipher.init(Cipher.DECRYPT_MODE, keyspec, new IvParameterSpec(
//                new byte[cipher.getBlockSize()]));
//        byte[] original = cipher.doFinal(encrypted1);
//        return new String(original, "UTF-8");
//    }


//    // /** 算法/模式/填充 **/
//    private static final String CipherMode = "AES/CBC/NoPadding";
////    private static final String CipherMode = "AES/CBC/PKCS5Padding";
//
//    /**
//     * 生成一个AES密钥对象
//     *
//     * @return
//     */
//    public static SecretKeySpec generateKey() {
//        try {
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            kgen.init(128, new SecureRandom());
//            SecretKey secretKey = kgen.generateKey();
//            byte[] enCodeFormat = secretKey.getEncoded();
//            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
//            return key;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 生成一个AES密钥字符串
//     *
//     * @return
//     */
//    public static String generateKeyString() {
//        return byte2hex(generateKey().getEncoded());
//    }
//
//    /**
//     * 加密字节数据
//     *
//     * @param content
//     * @param key
//     * @return
//     */
//    public static byte[] encrypt(byte[] content, byte[] key) {
//        try {
//            Cipher cipher = Cipher.getInstance(CipherMode);
//            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(
//                    new byte[cipher.getBlockSize()]));
//            byte[] result = cipher.doFinal(content);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 通过byte[]类型的密钥加密String
//     *
//     * @param content
//     * @param key
//     * @return 16进制密文字符串
//     */
//    public static String encrypt(String content, byte[] key) {
//        try {
//            Cipher cipher = Cipher.getInstance(CipherMode);
//            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(
//                    new byte[cipher.getBlockSize()]));
//            byte[] data = cipher.doFinal(content.getBytes("UTF-8"));
//            String result = byte2hex(data);
//            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 通过String类型的密钥加密String
//     *
//     * @param content
//     * @param key
//     * @return 16进制密文字符串
//     */
//    public static String encrypt(String key, String content) throws Exception {
//        byte[] data = null;
//        data = content.getBytes("UTF-8");
//        data = encrypt(data, new SecretKeySpec(hex2byte(key), "AES").getEncoded());
//        String result = byte2hex(data);
//        return result;
//    }
//
//    /**
//     * 通过byte[]类型的密钥解密byte[]
//     *
//     * @param content
//     * @param key
//     * @return
//     */
//    public static byte[] decrypt(byte[] content, byte[] key) throws Exception {
//        Cipher cipher = Cipher.getInstance(CipherMode);
//        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(
//                new byte[cipher.getBlockSize()]));
//        byte[] result = cipher.doFinal(content);
//        return result;
//    }
//
//    /**
//     * 通过String类型的密钥 解密String类型的密文
//     *
//     * @param content
//     * @param key
//     * @return
//     */
//    public static String decrypt(String key, String content) throws Exception {
//        byte[] data = null;
//        data = hex2byte(content.substring(1, content.length() - 1).trim());
//        data = decrypt(data, hex2byte(key));
//        if (data == null)
//            return null;
//        String result = null;
//        result = new String(data, "UTF-8");
//        return result;
//    }
//
//    /**
//     * 通过byte[]类型的密钥 解密String类型的密文
//     *
//     * @param content
//     * @param key
//     * @return
//     */
//    public static String decrypt(String content, byte[] key) {
//        try {
//            Cipher cipher = Cipher.getInstance(CipherMode);
//            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(
//                    new byte[cipher.getBlockSize()]));
//            byte[] data = cipher.doFinal(hex2byte(content));
//            return new String(data, "UTF-8");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 字节数组转成16进制字符串
//     *
//     * @param b
//     * @return
//     */
//    public static String byte2hex(byte[] b) { // 一个字节的数，
//        StringBuffer sb = new StringBuffer(b.length * 2);
//        String tmp;
//        for (int n = 0; n < b.length; n++) {
//            // 整数转成十六进制表示
//            tmp = (Integer.toHexString(b[n] & 0XFF));
//            if (tmp.length() == 1) {
//                sb.append("0");
//            }
//            sb.append(tmp);
//        }
//        return sb.toString().toUpperCase(); // 转成大写
//    }
//
//    /**
//     * 将hex字符串转换成字节数组
//     *
//     * @param inputString
//     * @return
//     */
//    private static byte[] hex2byte(String inputString) {
//        if (inputString == null || inputString.length() < 2) {
//            return new byte[0];
//        }
//        inputString = inputString.toLowerCase();
//        int l = inputString.length() / 2;
//        byte[] result = new byte[l];
//        for (int i = 0; i < l; ++i) {
//            String tmp = inputString.substring(2 * i, 2 * i + 2);
//            result[i] = (byte) (Integer.parseInt(tmp, 16) & 0xFF);
//        }
//        return result;
//    }


//    private final static String AES_CIPHER = "AES/CBC/NoPadding";
//    private final static String AES_CIPHER = "AES/CBC/PKCS5Padding";
    private final static String AES_CIPHER = "AES/CBC/PKCS7Padding";
    private final static String AES_SECRET = "AES";

    public static byte[] genKey() {
        byte[] result = null;
        try {
            // KeyGenerator提供对称密钥生成器的功能，支持各种算法
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom());
            // SecretKey负责保存对称密钥
            SecretKey deskey = keygen.generateKey();
            result = deskey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            //log.warn("[AES密钥生成]系统不支持AES算法", e);
        }
        return result;
    }

    public static byte[] genMD5Key() {
        byte[] result = null;
        try {
            // KeyGenerator提供对称密钥生成器的功能，支持各种算法
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom());
            // SecretKey负责保存对称密钥
            SecretKey deskey = keygen.generateKey();
            result = deskey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            //log.warn("[AES密钥生成]系统不支持AES算法", e);
        }
        return result;
    }

    private static final char[] h = "0123456789ABCDEF".toCharArray();

    private static Map<Character, Integer> hs = new HashMap<Character, Integer>();

    static {
        for (int i = 'A'; i <= 'F'; i++) {
            hs.put(((char) i), i - 'A' + 10);
        }
        for (int i = 'a'; i <= 'f'; i++) {
            hs.put(((char) i), i - 'a' + 10);
        }
        for (int i = '0'; i <= '9'; i++) {
            hs.put(((char) i), i - '0');
        }
    }

    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    public static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * AES加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
//    public static String encrypt(String key, String data) {
//        LogUtils.LogE("[AES加密]加密前--明文 : " + data + "; 加密密钥 : " + key);
//        int len = data.length();
//        StringBuilder d = new StringBuilder();
//        d.append(leftFill(len, 2, "0"));
//        d.append(rightFill(data, 14, "F"));
//        LogUtils.LogE("d==="+d);
//        String tmp = null;
//        byte[] tmpB = encrypt((tmp = d.toString()).getBytes(), h2b(key));
//        String result = h2s(tmpB);
//        LogUtils.LogE("[AES加密]加密后--明文 : " + data + "(" + tmp + "); 加密密钥 : " + key + "; 加密密文 : " + result);
//        return result;
//    }

    /**
     * AES解密
     *
     * @param data 密文
     * @param key  密钥
     * @return 明文
     */
//    public static String decrypt(String key, String data) {
//        data = data.substring(1, data.length() - 1);
//        byte[] bytes = decrypt(h2b(data), h2b(key));
//        String result = bytes == null ? null : new String(bytes);
//        if (result != null && result.length() > 1) {
//            int len = Integer.valueOf(result.substring(0, 2));
//            if (len + 2 <= result.length())
//                result = result.substring(2, len + 2);
//        }
//        return result;
//    }
//
//    public static String decrypt2(String key, String data) {
//        byte[] bytes = decrypt(h2b(data), h2b(key));
//        String result = bytes == null ? null : new String(bytes);
//        LogUtils.LogE("result===="+result);
//        if (result != null && result.length() > 1) {
//            int len = Integer.valueOf(result.substring(0, 2));
//            if (len + 2 <= result.length())
//                result = result.substring(2, len + 2);
//        }
//        return result;
//    }

    /**
     * AES解密
     *
     * @param data 密文
     * @param key  密钥
     * @return 明文
     */
    public static String decrypt(String key, String data) {
        String result = "";
        try {
            byte[] bytes = decrypt(h2b(data), h2b(key));
            result = bytes == null ? null : new String(bytes);
        } catch (Exception e) {
            result = data;
        }
        return result;
    }

    /**
     * AES解密算法
     *
     * @param data 待解密密文
     * @param key  解密密钥
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        byte[] decryptedData = null;

        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER);// 创建密码器
            IvParameterSpec iv = new IvParameterSpec(key);
            // 提供的个任意长度的密码，通过它生成。
            SecretKeySpec sp = new SecretKeySpec(createKey(key), AES_SECRET);

            cipher.init(Cipher.DECRYPT_MODE, sp, iv);// 初始化

            // 执行加密操作
            decryptedData = cipher.doFinal(data);

        } catch (NoSuchAlgorithmException e) {
            //log.warn("[AES解密]系统不支持AES算法", e);
        } catch (InvalidKeyException e) {
            //log.warn("[AES解密]解法的密钥格式", e);
        } catch (NoSuchPaddingException e) {
            //log.warn("[AES解密]解密算法运算出错", e);
        } catch (IllegalBlockSizeException e) {
            //log.warn("[AES解密]解密数据块大小不正确", e);
        } catch (BadPaddingException e) {
            //log.warn("[AES解密]错误的解密数据封装", e);
        } catch (InvalidAlgorithmParameterException e) {
            //log.warn("[AES加密]错误的解密算法参数", e);
        }

        return decryptedData;
    }

    public static byte[] createKey(byte[] key) {
        return key;
//      String algorithmName = "";
//      try {
//          algorithmName = "AES";
//          KeyGenerator kgen = KeyGenerator.getInstance(algorithmName);
//          algorithmName = "SHA1PRNG";
//          SecureRandom secureRandom = SecureRandom.getInstance(algorithmName);
//          secureRandom.setSeed(key);
//          kgen.init(128, secureRandom);
//          SecretKey secretKey = kgen.generateKey();
//          return secretKey.getEncoded();
//      } catch (NoSuchAlgorithmException e) {
//          log.warn("[密钥生成]系统不支持此算法", e);
//      }
//      return null;
    }

    /**
     * MD5 校验
     *
     * @param data
     * @return
     */
    public static String md5(String data) {

        String result = null;
        try {
            result = h2s(md5(data.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            //log.warn("[MD5加密]程序不支持 UTF-8 编码");
        }
        LogUtils.LogE("[MD5加密]校验结果 : " + result + "; 校验内容 : " + data);
        return result;
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     */
    public static byte[] md5(byte[] data) {

        byte[] result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(data);
            result = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            //log.warn("[MD5加密]不支持MD5算法", e);
        }

        return result;
    }

    /**
     * 十六进制字符串转二进制数据流
     *
     * @param hex
     * @return
     */
    public static byte[] h2b(String hex) {

        byte[] bs = new byte[hex.length() / 2];
        int i = 0;
        char[] s = hex.toCharArray();
        for (int j = 0; j < s.length; ) {
            bs[i++] = (byte) ((hs.get(s[j++]) << 4) | hs.get(s[j++]));
        }
        return bs;
    }

    /**
     * 二进制数据流转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String h2s(byte[] bytes) {

        StringBuilder s = new StringBuilder();
        for (byte b : bytes) {
            s.append(h[(b >> 4) & 0xF]);
            s.append(h[b & 0xF]);
        }

        return s.toString();
    }

    /**
     * 左补充
     *
     * @param obj
     * @param len
     * @param fill
     * @return
     */
    public static String leftFill(Object obj, int len, String fill) {

        if (obj == null)
            return null;
        String src = String.valueOf(obj);
        StringBuilder s = new StringBuilder();
        for (int l = src.length(); l < len; l++) {
            s.append(fill);
        }
        s.append(src);
        return s.toString();
    }

    /**
     * 右补充
     *
     * @param obj
     * @param len
     * @param fill
     * @return
     */
    public static String rightFill(Object obj, int len, String fill) {

        if (obj == null)
            return null;
        String src = String.valueOf(obj);
        StringBuilder s = new StringBuilder();
        s.append(src);
        for (int l = src.length(); l < len; l++) {
            s.append(fill);
        }
        return s.toString();
    }


    /**
     * AES加密
     *
     * @param data 明文
     * @param key  密钥
     * @return 密文
     */
    public static String encrypt(String key, String data) {
//        LogUtils.LogE("[AES加密]加密前--明文 : " + data + "; 加密密钥 : " + key);
        String result = "";
        try {
            result = h2s(encrypt(data.getBytes(), h2b(key)));
        } catch (Exception e) {
            result = data;
        }
//        LogUtils.LogE("[AES加密]加密后--密文 : " + result + "; 加密密钥 : " + key);
        return result;
    }

    /**
     * AES加密方法
     *
     * @param data 待加密明文
     * @param key  加密密钥
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {

        byte[] encryptedData = null;
        try {
            Cipher cipher = Cipher.getInstance(AES_CIPHER);// 创建密码器
            IvParameterSpec iv = new IvParameterSpec(key);
            SecretKeySpec sp = new SecretKeySpec(createKey(key), AES_SECRET);

            cipher.init(Cipher.ENCRYPT_MODE, sp, iv);// 初始化
            // 执行加密操作
            encryptedData = cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            //log.warn("[AES加密]系统不支持AES算法", e);
        } catch (InvalidKeyException e) {
            //log.warn("[AES加密]非法的密钥格式", e);
        } catch (NoSuchPaddingException e) {
            //log.warn("[AES加密]加密算法运算出错", e);
        } catch (IllegalBlockSizeException e) {
            //log.warn("[AES加密]加密数据块大小不正确", e);
        } catch (BadPaddingException e) {
            //log.warn("[AES加密]错误的加密数据封装", e);
        } catch (InvalidAlgorithmParameterException e) {
            //log.warn("[AES加密]错误的加密算法参数", e);
        }
        return encryptedData;
    }

    public static void main(String[] args) {
        String key = "10FBBC27D46E1F15541DBCD710808EBF";
        String data = "15359341044_1108871";
        String encrypt = AES.encrypt(CommonHelper.md5(UserManager.dataKey + Config.DATAKEY_SALT), data);
        System.out.println("-----------------：" + encrypt);

//        System.out.println("encrypt(byte[] data, byte[] key)---：" + encrypt(data.getBytes(), h2b(key)));
//        System.out.println("encrypt(String key, String data)---：" + encrypt(key,data));
    }
}
