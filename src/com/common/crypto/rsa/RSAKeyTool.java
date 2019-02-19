package com.common.crypto.rsa;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.log4j.Logger;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAKeyTool {
	private static Logger	logger			= Logger.getLogger( RSAKeyTool.class );

	static BASE64Decoder	Base64Decoder	= new BASE64Decoder();
	static BASE64Encoder	Base64Encoder	= new BASE64Encoder();

	static KeyFactory		keyFactory		= null;

	static {
		try {
			keyFactory = KeyFactory.getInstance( "RSA" );
		} catch (Exception e) {
			logger.error( "error when create rsa factory:", e );
		}
	}

	public static RSAPublicKey getPubKey(String pubKey) {
		try {
			byte[] buffer = Base64Decoder.decodeBuffer( pubKey );
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec( buffer );
			return (RSAPublicKey) keyFactory.generatePublic( keySpec );
		} catch (Exception e) {
			logger.error( "error when create public key:", e );
		}
		return null;
	}

	public static RSAPrivateKey getPkcs1FormatPriKey(String priKey) {
		try {
			byte[] buffer = Base64Decoder.decodeBuffer( priKey );
			RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure( (ASN1Sequence) ASN1Sequence.fromByteArray( buffer ) );
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec( asn1PrivKey.getModulus(), asn1PrivKey.getPrivateExponent() );
			return (RSAPrivateKey) keyFactory.generatePrivate( keySpec );
		} catch (Exception e) {
			logger.error( "error when create pkcs1 private key:", e );
		}
		return null;
	}

	public static RSAPrivateKey getPkcs8FormatPriKey(String priKey) {
		try {
			byte[] buffer = Base64Decoder.decodeBuffer( priKey );
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec( buffer );
			return (RSAPrivateKey) keyFactory.generatePrivate( keySpec );
		} catch (Exception e) {
			logger.error( "error when create pkcs8 private key:", e );
		}
		return null;
	}
}
