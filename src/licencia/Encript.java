package licencia;

import java.util.Base64;

import javax.crypto.KeyGenerator;

import java.security.Key;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;

public class Encript {
	private static String  ENCRYPT_KEY="clave_dome201013";

	public String encript(String text) throws Exception {	
		//System.out.println(text);
		Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);

		byte[] encrypted = cipher.doFinal(text.getBytes());
		String salida =	Base64.getEncoder().encodeToString(encrypted);
		//System.out.println(salida);
		return salida;
		}

	public String decrypt(String encrypted) throws Exception {
		byte[] encryptedBytes=Base64.getDecoder().decode( encrypted.replace("\n", "") );
			
		Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);

		String decrypted = new String(cipher.doFinal(encryptedBytes));
	        
		return decrypted;
		}

}
