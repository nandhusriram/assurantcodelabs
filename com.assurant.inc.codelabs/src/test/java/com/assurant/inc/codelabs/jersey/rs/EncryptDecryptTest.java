package com.assurant.inc.codelabs.jersey.rs;

import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.Test;

public class EncryptDecryptTest {

	@Test
	public void hashCC() throws Exception
	{
		byte[] salt = "choose a better salt".getBytes();
		String passphrase="Royals";
		int iterations = 10000;
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		SecretKey tmp = factory.generateSecret(new PBEKeySpec(passphrase.toCharArray(), 
				salt, iterations, 128));
		SecretKeySpec key = new SecretKeySpec(tmp.getEncoded(), "AES");
		
		Cipher aes = Cipher.getInstance("AES/ECB/PKCS5Padding");
		aes.init(Cipher.ENCRYPT_MODE, key);
		
		byte[] ciphertext = aes.doFinal("my cleartext".getBytes());

		aes.init(Cipher.DECRYPT_MODE, key);
		String cleartext = new String(aes.doFinal(ciphertext));
		System.out.println(cleartext);
	   
	    assertThat(cleartext,equalToIgnoringCase("my cleartext"));
	    assertThat(cleartext,is("my cleartext"));
	}
}
