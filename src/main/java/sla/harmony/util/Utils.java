/**
 * The MIT License (MIT)
 * 
 * Copyright (c) 2015 ShadowLordAlpha
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package sla.harmony.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class that contains useful methods used throughout the rest of the code or internally.
 * 
 * @since Harmony v0.2.0
 * @version v1.0.0
 * @author Josh "ShadowLordAlpha"
 */
public final class Utils {

	/**
	 * SLF4J logger instance for this class.
	 */
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);

	/**
	 * Private constructor to keep users from making instances of this class.
	 */
	private Utils() {

	}

	/**
	 * Helper method to check for the most basic of JSONObject errors and return a string.
	 * 
	 * @param data the String to check.
	 * @return {@code "{}"} if the string was empty or {@code null} else the original string.
	 */
	public static String checkObjString(String data) {
		if (data == null || data.isEmpty()) {
			data = "{}";
		}

		return data;
	}

	/**
	 * Helper method to check for the most basic of JSONArray errors and return a string.
	 * 
	 * @param data the String to check.
	 * @return {@code "[]"} if the string was empty or {@code null} else the original string.
	 */
	public static String checkArrString(String data) {
		if (data == null || data.isEmpty()) {
			data = "[]";
		}

		return data;
	}

	/**
	 * Helper method to read in a whole file to a string and return it. If the file is {@code null} this method returns
	 * null. If the file does not exist this method creates the file.
	 * 
	 * @param file The file to read or create.
	 * @return {@code null} or the contents of the file.
	 */
	public static String readFile(File file) {

		if (file == null) {
			return null;
		}

		if (!file.exists()) {
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdir();
			}

			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.warn("Failed to create new File at {}:", file.getAbsolutePath(), e);
			}
		}

		StringBuffer buffer = new StringBuffer();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			String line;
			while ((line = reader.readLine()) != null) {
				buffer.append(line).append("\n");
			}

			return buffer.toString();
		} catch (IOException e) {
			logger.warn("Failed to Properly read File at {}:", file.getAbsolutePath(), e);
		}

		return null;
	}

	/**
	 * Helper method to write to a file. This will create the file if the file does not exist. If the file is null this
	 * method returns immediately.
	 * 
	 * @param file The file to write to or create.
	 * @param data The data to write to the file.
	 * @return {@code null} or the contents of the file.
	 */
	public static void writeFile(File file, String data) {

		if (file == null) {
			return;
		}

		if (!file.exists()) {
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdir();
			}

			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.warn("Failed to create new File at {}:", file.getAbsolutePath(), e);
			}
		}

		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

			writer.write(data);

			return;
		} catch (IOException e) {
			logger.warn("Failed to Properly write to File at {}:", file.getAbsolutePath(), e);
		}

		return;
	}

	/**
	 * Helper method to append to a file. This will create the file if the file does not exist. If the file is null this
	 * method returns immediately.
	 * 
	 * @param file The file to write to or create.
	 * @param data The data to write to the file.
	 * @return {@code null} or the contents of the file.
	 */
	public static void appendFile(File file, String data) {

		if (file == null) {
			return;
		}

		if (!file.exists()) {
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdir();
			}

			try {
				file.createNewFile();
			} catch (IOException e) {
				logger.warn("Failed to create new File at {}:", file.getAbsolutePath(), e);
			}
		}

		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {

			writer.write(data);

			return;
		} catch (IOException e) {
			logger.warn("Failed to Properly write to File at {}:", file.getAbsolutePath(), e);
		}

		return;
	}

	/**
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static String encrypt(String key, String data) {

		if (data == null) {
			throw new NullPointerException("Input data can not be null!");
		}

		// Create a key to decrypt
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			logger.error("Hashing Algorithm not Found:", e);
			return null;
		}

		Random random = new Random(ByteBuffer.wrap((key + "Extender").getBytes(StandardCharsets.UTF_8)).getLong(0));
		byte[] bytes = new byte[100];
		random.nextBytes(bytes);
		key = key + new String(bytes, StandardCharsets.UTF_8);
		byte[] digestKey = Arrays.copyOf(md.digest(key.getBytes(StandardCharsets.UTF_8)), 24);

		// Encrypt the data
		SecretKey skey = new SecretKeySpec(digestKey, "DESede");
		IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			logger.error("Failed to get Cipher instance:", e);
			return null;
		}

		try {
			cipher.init(Cipher.ENCRYPT_MODE, skey, iv);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			logger.error("Failed to init cipher", e);
			return null;
		}

		try {
			return new String(Base64.getEncoder().encode(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8))),
					StandardCharsets.UTF_8);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Failed to encode data:", e);
			return null;
		}
	}

	/**
	 * 
	 * @param key
	 * @param data
	 * @return
	 */
	public static String decrypt(String key, String data) {

		if (data == null) {
			throw new NullPointerException("Input data can not be null!");
		}

		// Create a key to decrypt
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			logger.error("Hashing Algorithm not Found:", e);
			return null;
		}

		Random random = new Random(ByteBuffer.wrap((key + "Extender").getBytes(StandardCharsets.UTF_8)).getLong(0));
		byte[] bytes = new byte[100];
		random.nextBytes(bytes);
		key = key + new String(bytes, StandardCharsets.UTF_8);
		byte[] digestKey = Arrays.copyOf(md.digest(key.getBytes(StandardCharsets.UTF_8)), 24);

		// Decrypt the data
		SecretKey skey = new SecretKeySpec(digestKey, "DESede");
		IvParameterSpec iv = new IvParameterSpec(new byte[8]);
		Cipher decipher;
		try {
			decipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			logger.error("Failed to get Cipher instance:", e);
			return null;
		}

		try {
			decipher.init(Cipher.DECRYPT_MODE, skey, iv);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			logger.error("Failed to init cipher", e);
			return null;
		}

		try {
			return new String(decipher.doFinal(Base64.getDecoder().decode(data.getBytes(StandardCharsets.UTF_8))),
					StandardCharsets.UTF_8);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			logger.error("Failed to decode data:", e);
			return null;
		}
	}
}
