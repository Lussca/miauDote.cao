package controller;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Encrypt {
	 final static String ALGORITHM = "RSA";
	    public static final String JWT_ISSUER = "MiauDote.Cao";

	    public String toHash(String message) throws NoSuchAlgorithmException {
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(message.getBytes());
	        byte[] digest = md.digest();

	        StringBuffer hexString = new StringBuffer();
	        for(int i = 0; i < digest.length; i++) {
	            if((0xff & digest[i]) < 0x10) {
	                hexString.append("0" + Integer.toHexString((0xFF & digest[i])));
	            } else {
	                hexString.append(Integer.toHexString(0xFF & digest[i]));
	            }
	        }

	        return hexString.toString();
	    }

	    public List<String> generateKeys(String password) {
	    	List<String> keys = new ArrayList<String>();

	        try {
	            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
	            keyGen.initialize(1024);

	            final KeyPair keysG = keyGen.generateKeyPair();
	            byte[] publicKey = keysG.getPublic().getEncoded();
	            byte[] privateKey = keysG.getPrivate().getEncoded();

	            String publicKeyString = Base64.getEncoder().encodeToString(publicKey);
	            String privateKeyString = Base64.getEncoder().encodeToString(privateKey);

	            keys.add(publicKeyString);
	            keys.add(privateKeyString);

	            return keys;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return null;
	    } 
	    public static byte[] generateSalt(int length) {
	        SecureRandom random = new SecureRandom();
	        byte[] salt = new byte[length];
	        random.nextBytes(salt);
	        return salt;
	    }
	    
	    
	    
	}
