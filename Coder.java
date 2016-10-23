package com.awifi.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @ClassName:Coder
 * @Description:
 * @author root
 *
 */
@SuppressWarnings({ "restriction" })
public abstract class Coder {
	/**
	 * 
	 */
	public static final String KEY_SHA = "SHA";
	/**
	 * 
	 */
	public static final String KEY_MD5 = "MD5";
	/**
	 * 
	 */
	// MAC算法可选以下多种算法
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * 
	 */
	// BASE64解密
	/**
	 * @Title:decryptBASE64 @Description: @param key key @return @throws
	 *                      Exception Exception @return Coder @throws
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	// BASE64加密
	/**
	 * @Title:encryptBASE64 @Description: @param key key @return @throws
	 *                      Exception Exception @return String @throws
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}

	/**
	 * @Title:encodeHex @Description: @param bytes bytes @return @return
	 *                  String @throws
	 */
	public static String encodeHex(byte[] bytes) {
		StringBuffer buffer = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 0x10) {
				buffer.append("0");
			}
			buffer.append(Long.toString(bytes[i] & 0xff, 16));
		}
		return buffer.toString();
	}

	/**
	 * @Title:encodeMd5 @Description: @param source source @return @return
	 *                  String @throws
	 */
	public static String encodeMd5(String source) {
		return encodeMd5(source.getBytes());
	}

	/**
	 * @Title:encodeMd5 @Description: @param source source @return @return
	 *                  String @throws
	 */
	public static String encodeMd5(byte[] source) {
		try {
			return encodeHex(MessageDigest.getInstance("MD5").digest(source));
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	// SHA加密
	/**
	 * @Title:encryptSHA @Description: @param data data @return @throws
	 *                   Exception Exception @return byte[] @throws
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);
		return sha.digest();
	}

	// 初始化HMAC密钥
	/**
	 * @Title:initMacKey @Description: @return @throws Exception
	 *                   Exception @return String @throws
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	// HMAC加密
	/**
	 * @Title:encryptHMAC @Description: @param data data @param key
	 *                    key @return @throws Exception Exception @return
	 *                    byte[] @throws
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}

}
