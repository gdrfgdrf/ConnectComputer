package cn.gdrfgdrf.ConnectComputerServer.Utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author gdrfgdrf
 */
public class RSAUtils {
    private static final String RSA = "RSA";
    
    private static final String RSA_ECB = "RSA/ECB/PKCS1Padding";

    private static final int KEY_LENGTH = 2048;

    private static final int MAX_ENCRYPT_BLOCK = 245;
    private static final int MAX_DECRYPT_BLOCK = 256;
    private static final String CHECK_STRING = "Hello World";

    private RSAUtils() {}

    public static KeyPair generateRsaKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(KEY_LENGTH);

        return keyPairGenerator.genKeyPair();
    }

    public static byte[] publicEncryptToByte(CharSequence str, PublicKey publicKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return getBytes(str.toString().getBytes(), cipher, MAX_ENCRYPT_BLOCK);
    }
    public static byte[] publicEncryptToByte(byte[] data, PublicKey publicKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return getBytes(data, cipher, MAX_ENCRYPT_BLOCK);
    }
    public static CharSequence publicEncrypt(CharSequence str, PublicKey publicKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        return publicEncrypt(str.toString().getBytes(), publicKey);
    }
    public static CharSequence publicEncrypt(byte[] data, PublicKey publicKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return Base64.encodeBase64String(getBytes(data, cipher, MAX_ENCRYPT_BLOCK));
    }

    public static byte[] publicDecryptToByte(CharSequence str, PublicKey publicKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return getBytes(str.toString().getBytes(), cipher, MAX_DECRYPT_BLOCK);
    }
    public static byte[] publicDecryptToByte(byte[] data, PublicKey publicKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return getBytes(data, cipher, MAX_DECRYPT_BLOCK);
    }
    public static CharSequence publicDecrypt(CharSequence str, PublicKey publicKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        return publicDecrypt(Base64.decodeBase64(str.toString()), publicKey);
    }
    public static CharSequence publicDecrypt(byte[] encryptedData, PublicKey publicKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return new String(getBytes(encryptedData, cipher, MAX_DECRYPT_BLOCK));
    }

    public static byte[] privateDecryptToByte(CharSequence str, PrivateKey privateKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return getBytes(str.toString().getBytes(), cipher, MAX_DECRYPT_BLOCK);
    }
    public static byte[] privateDecryptToByte(byte[] data, PrivateKey privateKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return getBytes(data, cipher, MAX_DECRYPT_BLOCK);
    }
    public static CharSequence privateDecrypt(CharSequence str, PrivateKey privateKey)
            throws
            NoSuchPaddingException,
            IllegalBlockSizeException,
            NoSuchAlgorithmException,
            BadPaddingException,
            IOException,
            InvalidKeyException {
        return privateDecrypt(Base64.decodeBase64(str.toString()), privateKey);
    }
    public static CharSequence privateDecrypt(byte[] encryptedData, PrivateKey privateKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(getBytes(encryptedData, cipher, MAX_DECRYPT_BLOCK));
    }

    public static byte[] privateEncryptToByte(CharSequence str, PrivateKey privateKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return getBytes(str.toString().getBytes(), cipher, MAX_ENCRYPT_BLOCK);
    }
    public static byte[] privateEncryptToByte(byte[] data, PrivateKey privateKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return getBytes(data, cipher, MAX_ENCRYPT_BLOCK);
    }
    public static CharSequence privateEncrypt(CharSequence str, PrivateKey privateKey)
            throws
            NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        return privateEncrypt(str.toString().getBytes(), privateKey);
    }
    public static CharSequence privateEncrypt(byte[] data, PrivateKey privateKey)
            throws NoSuchPaddingException,
            NoSuchAlgorithmException,
            InvalidKeyException,
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        Cipher cipher = Cipher.getInstance(RSA_ECB);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return Base64.encodeBase64String(getBytes(data, cipher, MAX_ENCRYPT_BLOCK));
    }

    public static byte[] getBytes(byte[] data, Cipher cipher, int maxEncryptBlock)
            throws
            IllegalBlockSizeException,
            BadPaddingException,
            IOException {
        int inputLen = data.length;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxEncryptBlock) {
                cache = cipher.doFinal(data, offSet, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            os.write(cache, 0, cache.length);
            i++;
            offSet = i * maxEncryptBlock;
        }

        byte[] decryptedData = os.toByteArray();
        os.close();
        return decryptedData;
    }

    public static boolean validateKey(PrivateKey privateKey, PublicKey publicKey) {
        try {
            CharSequence privateEncrypt = privateEncrypt(CHECK_STRING, privateKey);
            publicDecrypt(privateEncrypt, publicKey);

            CharSequence publicEncrypt = publicEncrypt(CHECK_STRING, publicKey);
            privateDecrypt(publicEncrypt, privateKey);

            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean validateKey(CharSequence privateKeyStr, CharSequence publicKeyStr) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyStr);
            PublicKey publicKey = getPublicKey(publicKeyStr);

            return validateKey(privateKey, publicKey);
        } catch (Exception ignored) {
            return false;
        }
    }

    public static PublicKey getPublicKey(CharSequence key)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] byteKey = Base64.decodeBase64(key.toString());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);

        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    public static PrivateKey  getPrivateKey(CharSequence key)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] byteKey = Base64.decodeBase64(key.toString());
        PKCS8EncodedKeySpec x509EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);

        return keyFactory.generatePrivate(x509EncodedKeySpec);
    }

}
