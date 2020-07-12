package com.neusoft.mid.cloong.common.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import com.sun.crypto.provider.SunJCE;
/**
 * 
 * 3DES加密算法
 * @author <a href="mailto:liu-qy@neusoft.com">liu-qy</a>
 * @version $Revision 1.0 $ 2014年2月19日 下午1:27:27
 */
public final class TripleDES {
    /**
     * 
     * 创建一个新的实例 
     */
    private TripleDES() {
    }

    /**
     * 加密
     * @param key 秘钥
     * @param data 加密数据
     * @return 加密字节
     */
    public static byte[] encrypt(byte[] key, byte[] data) {
        try {
            Security.insertProviderAt(new SunJCE(), 1);
            DESedeKeySpec dks = new DESedeKeySpec(key, 0);
            // SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("TripleDES");
            SecretKey skey = keyFactory.generateSecret(dks);
            // Get a cipher object
            Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            // Encrypt
            cipher.init(Cipher.ENCRYPT_MODE, skey, iv);
            if (data.length % 8 != 0) {
                byte[] pdata = new byte[(data.length / 8 + 1) * 8];
                System.arraycopy(data, 0, pdata, 0, data.length);
                return cipher.doFinal(pdata);
            }
            return cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密
     * @param key 秘钥
     * @param encryptedData 加密数据
     * @return 明文
     */
    public static byte[] decrypt(byte[] key, byte[] encryptedData) {
        Security.addProvider(new SunJCE());
        byte[] decryptedData = null;
        try {
            DESedeKeySpec dks = new DESedeKeySpec(key);
            // SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("TripleDES");
            SecretKey skey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DESede/CBC/NoPadding");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            cipher.init(Cipher.DECRYPT_MODE, skey, iv);
            decryptedData = cipher.doFinal(encryptedData);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return decryptedData;
    }
    /**
     * 生成秘钥
     * @return 秘钥字节
     */
    public static byte[] gen3DESKey() {
        byte[] key = null;
        try {
            SecureRandom sr = new SecureRandom();
            // KeyGenerator kg = KeyGenerator.getInstance("DESede");
            KeyGenerator kg = KeyGenerator.getInstance("TripleDES");
            kg.init(sr);
            SecretKey skey = kg.generateKey();
            key = skey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return key;
    }
    /**
     * 生成秘钥
     * @param strKey 秘钥明文
     * @return 秘钥字节
     */
    public static byte[] gen3DESKey(String strKey) {
        byte[] key = null;
        try {
            // KeyGenerator kg = KeyGenerator.getInstance("DESede");
            KeyGenerator kg = KeyGenerator.getInstance("TripleDES");
            kg.init(new SecureRandom(strKey.getBytes()));
            SecretKey skey = kg.generateKey();
            key = skey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return key;
    }

}
