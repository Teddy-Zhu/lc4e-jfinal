package com.teddy.jfinal.tools;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;


public class SecurityIDEATool {

    @SuppressWarnings("unused")
    private static Logger log = Logger.getLogger(SecurityIDEATool.class);

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "IDEA";

    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     */
    public static final String CIPHER_ALGORITHM = "IDEA/ECB/PKCS5Padding";

    static {
        // 加入BouncyCastleProvider支持
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Const 密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) throws Exception {
        // 生成秘密密钥
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);

        return secretKey;
    }

    /**
     * 解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);

        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 初始化，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k);

        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 还原密钥
        Key k = toKey(key);

        // 实例化
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);

        // 初始化，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k);

        // 执行操作
        return cipher.doFinal(data);
    }

    /**
     * 生成密钥 <br>
     *
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws Exception {
        // 实例化
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);

        // 初始化
        kg.init(128);

        // 生成秘密密钥
        SecretKey secretKey = kg.generateKey();

        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    /**
     * 测试
     *
     * @throws Exception
     */
    public static void main() throws Exception {
        String inputStr = "IDEA";
        byte[] inputData = inputStr.getBytes();
        System.err.println("原文:\t" + inputStr);

        // 初始化密钥
        byte[] key = initKey();
        System.err.println("密钥:\t" + Base64.encodeBase64String(key));

        // 加密
        inputData = encrypt(inputData, key);
        System.err.println("加密后:\t" + Base64.encodeBase64String(inputData));

        // 解密
        byte[] outputData = decrypt(inputData, key);

        String outputStr = new String(outputData);
        System.err.println("解密后:\t" + outputStr);
    }
}
