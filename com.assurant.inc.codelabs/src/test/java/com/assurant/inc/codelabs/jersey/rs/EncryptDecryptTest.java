package com.assurant.inc.codelabs.jersey.rs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.junit.Assert.assertThat;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

public class EncryptDecryptTest {

	private static final int HASH_BYTE_SIZE = 128; // 512 bits
	
	private byte[] generateSalt() throws Exception
	{
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		byte salt[] = new byte[HASH_BYTE_SIZE]; // use salt size at least as long as hash
	    random.nextBytes(salt);
		return salt;
	}
	@Test
	public void hashCC() throws Exception
	{
		String passphrase="Royals";
		int iterations = 20000;
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey tmp = factory.generateSecret(new PBEKeySpec(passphrase.toCharArray(), 
				generateSalt(), iterations, 128));
		SecretKeySpec key = new SecretKeySpec(tmp.getEncoded(), "AES");
		
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		aes.init(Cipher.ENCRYPT_MODE, key);
		
		byte[] ciphertext = aes.doFinal("my cleartext".getBytes());
		//System.out.println(new String(ciphertext));
		aes.init(Cipher.DECRYPT_MODE, key);
		String cleartext = new String(aes.doFinal(ciphertext));
		System.out.println(cleartext);
	   
	    assertThat(cleartext,equalToIgnoringCase("my cleartext"));
	    assertThat(cleartext,is("my cleartext"));
	}
}
