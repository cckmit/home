/*******************************************************************************
 * @(#)PasswordGen.java 2014年1月15日
 *
 * Copyright 2014 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.neusoft.mid.cloong.common.util;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.RandomStringUtils;

/**
 * 常用工具包。包括生成各种密码随机串，加密解密，编码解码，执行url等
 * @author <a href="mailto:feng.jian@neusoft.com">feng jian</a>
 * @version $Revision 1.0 $ 2014年1月15日 下午6:25:06
 */

public class PasswordTool {
    /**
     * 生成密码.
     * @param count 密码位数
     * @param letters 是否包含字符
     * @param numbers 是否包含数字
     * @return String password
     */
    public static String getPassword(int count, boolean letters, boolean numbers) {
        return RandomStringUtils.random(count, letters, numbers);
    }

    /**
     * 生成字符数字混合的密码.
     * @param count 密码位数
     * @return String password
     */
    public static String getPassword(int count) {
        return getPassword(count, true, true);
    }

    /**
     * 生成纯数字密码.
     * @param count 密码位数
     * @return String password
     */
    public static String getPasswordOfNumber(int count) {
        return getPassword(count, false, true);
    }

    /**
     * 生成纯字符密码.
     * @param count 密码位数
     * @return String password
     */
    public static String getPasswordOfCharacter(int count) {
        return getPassword(count, true, false);
    }

    /**
     * 生成3DES密钥.
     * @param key_byte seed key
     * @throws Exception
     * @return javax.crypto.SecretKey Generated DES key
     */
    public static javax.crypto.SecretKey genDESKey(byte[] key_byte) throws Exception {
        SecretKey k = new SecretKeySpec(key_byte, "DESede");
        return k;
    }

    /**
     * 3DES 解密(byte[]).
     * @param key SecretKey
     * @param crypt byte[]
     * @throws Exception
     * @return byte[]
     */
    public static byte[] desDecrypt(javax.crypto.SecretKey key, byte[] crypt) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(crypt);
    }

    /**
     * 3DES 解密(String).
     * @param key SecretKey
     * @param crypt byte[]
     * @throws Exception
     * @return byte[]
     */
    public static String desDecrypt(javax.crypto.SecretKey key, String crypt) throws Exception {
        return new String(desDecrypt(key, crypt.getBytes()));
    }

    /**
     * 3DES加密(byte[]).
     * @param key SecretKey
     * @param src byte[]
     * @throws Exception
     * @return byte[]
     */
    public static byte[] desEncrypt(javax.crypto.SecretKey key, byte[] src) throws Exception {
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("DESede");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(src);
    }

    /**
     * 3DES加密(String).
     * @param key SecretKey
     * @param src byte[]
     * @throws Exception
     * @return byte[]
     */
    public static String desEncrypt(javax.crypto.SecretKey key, String src) throws Exception {
        return new String(desEncrypt(key, src.getBytes()));
    }

    /**
     * MD5 摘要计算(byte[]).
     * @param src byte[]
     * @throws Exception
     * @return byte[] 16 bit digest
     */
    public static byte[] md5Digest(byte[] src) throws Exception {
        java.security.MessageDigest alg = java.security.MessageDigest.getInstance("MD5");
        // MD5 is 16 bit message digest
        return alg.digest(src);
    }

    /**
     * MD5 摘要计算(String).
     * @param src String
     * @throws Exception
     * @return String
     */
    public static String md5Digest(String src) throws Exception {
        return new String(md5Digest(src.getBytes()));
    }

    /**
     * BASE64 编码.
     * @param src String inputed string
     * @return String returned string
     */
    public static String base64Encode(String src) {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encode(src.getBytes());
    }

    /**
     * BASE64 编码(byte[]).
     * @param src byte[] inputed string
     * @return String returned string
     */
    public static String base64Encode(byte[] src) {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encode(src);
    }

    /**
     * BASE64 解码.
     * @param src String inputed string
     * @return String returned string
     */
    public static String base64Decode(String src) {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            return new String(decoder.decodeBuffer(src));
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * BASE64 解码(to byte[]).
     * @param src String inputed string
     * @return String returned string
     */
    public static byte[] base64DecodeToBytes(String src) {
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            return decoder.decodeBuffer(src);
        } catch (Exception ex) {
            return null;
        }
    }
}
