package com.assurant.inc.codelabs.crypto;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeyStoreUtil {

	private static final Logger LOG = LoggerFactory
			.getLogger(KeyStoreUtil.class);

	public static Key getKeyFromKeyStore(final String keystoreLocation,final String keystorePass, final String alias, final String keyPass)
	{
		try 
		{
			InputStream keystoreStream = new FileInputStream(keystoreLocation);
			KeyStore keystore = KeyStore.getInstance("JCEKS");
			keystore.load(keystoreStream, keystorePass.toCharArray());
			LOG.debug("Keystore with alias {} found == {}", alias,keystore.containsAlias(alias));
			if (!keystore.containsAlias(alias)) 
			{
				throw new RuntimeException("Alias for key not found");
			}
			Key key = keystore.getKey(alias, keyPass.toCharArray());
			LOG.debug("Key Found {} -> {}", key.getAlgorithm(), new Base64(Boolean.TRUE).encode(key.getEncoded()));
			return key;

		} catch (Throwable e) 
		{
			throw new RuntimeException(e);
		}
	}

}
