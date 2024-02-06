package cn.gdrfgdrf.ConnectComputerServer.Utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author gdrfgdrf
 */
public class AESUtils {
    public static final String AES = "AES";

    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final String AES_CFB = "AES/CFB/PKCS5Padding";
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";

    private AESUtils() {}

    public static IvParameterSpec generateIv() throws NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance(AES);
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[cipher.getBlockSize()];
        secureRandom.nextBytes(iv);

        return new IvParameterSpec(iv);
    }

    public static SecretKey generateKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
        keyGenerator.init(256);

        return keyGenerator.generateKey();
    }

    public static byte[] encryptToByteByCBC(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return doEncrypt(str.getBytes(), ivParameterSpec, secretKey, AES_CBC);
    }

    public static byte[] encryptToByteByCBC(byte[] data, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return doEncrypt(data, ivParameterSpec, secretKey, AES_CBC);
    }

    public static String encryptByCBC(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return encryptByCBC(str.getBytes(), ivParameterSpec, secretKey);
    }

    public static String encryptByCBC(byte[] data, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return Base64.encodeBase64String(doEncrypt(data, ivParameterSpec, secretKey, AES_CBC));
    }

    public static byte[] decryptToByteByCBC(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return decryptToByteByCBC(Base64.decodeBase64(str), ivParameterSpec, secretKey);
    }

    public static byte[] decryptToByteByCBC(byte[] encryptedData, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return doDecrypt(encryptedData, ivParameterSpec, secretKey, AES_CBC);
    }

    public static String decryptByCBC(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return decryptByCBC(Base64.decodeBase64(str), ivParameterSpec, secretKey);
    }

    public static String decryptByCBC(byte[] encryptedData, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return new String(doDecrypt(encryptedData, ivParameterSpec, secretKey, AES_CBC));
    }

    public static byte[] encryptToByteByCFB(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return doEncrypt(str.getBytes(), ivParameterSpec, secretKey, AES_CFB);
    }

    public static byte[] encryptToByteByCFB(byte[] data, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return doEncrypt(data, ivParameterSpec, secretKey, AES_CFB);
    }

    public static String encryptByCFB(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return encryptByCFB(str.getBytes(), ivParameterSpec, secretKey);
    }

    public static String encryptByCFB(byte[] data, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return Base64.encodeBase64String(doEncrypt(data, ivParameterSpec, secretKey, AES_CFB));
    }

    public static byte[] decryptToByteByCFB(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return decryptToByteByCFB(Base64.decodeBase64(str), ivParameterSpec, secretKey);
    }

    public static byte[] decryptToByteByCFB(byte[] encryptedData, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return doDecrypt(encryptedData, ivParameterSpec, secretKey, AES_CFB);
    }

    public static String decryptByCFB(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return decryptByCFB(Base64.decodeBase64(str), ivParameterSpec, secretKey);
    }

    public static String decryptByCFB(byte[] encryptedData, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return new String(doDecrypt(encryptedData, ivParameterSpec, secretKey, AES_CFB));
    }

    public static byte[] encryptToByteByECB(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return doEncrypt(str.getBytes(), ivParameterSpec, secretKey, AES_ECB);
    }

    public static byte[] encryptToByteByECB(byte[] data, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return doEncrypt(data, ivParameterSpec, secretKey, AES_ECB);
    }

    public static String encryptByECB(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return encryptByECB(str.getBytes(), ivParameterSpec, secretKey);
    }

    public static String encryptByECB(byte[] data, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return Base64.encodeBase64String(doEncrypt(data, ivParameterSpec, secretKey, AES_ECB));
    }

    public static byte[] decryptToByteByECB(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return decryptToByteByECB(Base64.decodeBase64(str), ivParameterSpec, secretKey);
    }

    public static byte[] decryptToByteByECB(byte[] encryptedData, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return doDecrypt(encryptedData, ivParameterSpec, secretKey, AES_ECB);
    }

    public static String decryptByECB(String str, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return decryptByECB(Base64.decodeBase64(str), ivParameterSpec, secretKey);
    }

    public static String decryptByECB(byte[] encryptedData, IvParameterSpec ivParameterSpec, SecretKey secretKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        return new String(doDecrypt(encryptedData, ivParameterSpec, secretKey, AES_ECB));
    }

    private static byte[] doEncrypt(byte[] data, IvParameterSpec ivParameterSpec, SecretKey secretKey, String transformation)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(data);
    }

    private static byte[] doDecrypt(byte[] encryptedData, IvParameterSpec ivParameterSpec, SecretKey secretKey, String transformation)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException {
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
        return cipher.doFinal(encryptedData);
    }

    public static SecretKey getKey(String str) {
        byte[] byteKey = Base64.decodeBase64(str);
        return new SecretKeySpec(byteKey, AES);
    }

}
