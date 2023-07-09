package controller.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import model.Dao;

public class JwtHandler {
Dao dao = new Dao();
	private static String getSecretKey(String key) throws IOException {
		File f = new File("C:\\Projetos\\miauDote.cao\\admin\\secretKey.ini");
		//File f = new File("C:\\Users\\lpereira\\Desktop\\miauDote.cao\\admin\\secretKey.ini");
		if(!f.exists()){
			 f = new File("C:\\Users\\Joao Gabriel\\Desktop\\miauDote.cao\\admin\\secretKey.ini");
		}
		FileInputStream fis = new FileInputStream(f);
		 Properties prop = new Properties();
		 try {
	        
	         prop.load(fis);
	         return prop.getProperty("secretKey");
	     } catch (IOException ex) {
	         ex.printStackTrace();
	         return null;
	     } finally {
	         if (fis != null) {
	             try {
	                 fis.close();
	             } catch (IOException e) {
	                 e.printStackTrace();
	             }
	         }
	     }
	 }

public String createJWT(String id, String issuer, String subject, long ttlMillis){
	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
	long nowMillis = System.currentTimeMillis();
	Date now  = new Date(nowMillis);
	
	byte[] apiKeySecretBytes = null;
	try {
		apiKeySecretBytes = getSecretKey("secretKey").getBytes();
	} catch (IOException e) {
		e.printStackTrace();
	}
	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	@SuppressWarnings("deprecation")
	JwtBuilder builder = Jwts.builder().setId(id)
			.setIssuedAt(now)
			.setSubject(subject)
			.setIssuer(issuer)
			.signWith(signatureAlgorithm, signingKey);
	if(ttlMillis > 0) {
		long expMillis = nowMillis + (ttlMillis * 24 * 60 * 60 * 1000);
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);
	}
	return builder.compact();
}
private static Claims decodeJWT(String jwt) throws SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, IOException {
	String secretKey = getSecretKey("secretKey");
	try {
	Claims claims = Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
            .build()
            .parseClaimsJws(jwt)
            .getBody();
		return claims;
	}catch(ExpiredJwtException e) {
		return null;
	}

}

public boolean validateJWT(String reqJwt, String login, boolean isOng) throws ClassNotFoundException, IOException, SQLException {
	String dbJwt = dao.getJWT(login, isOng);
	try {
	Claims dbClaims = decodeJWT(dbJwt);
	Claims reqClaims = decodeJWT(reqJwt);
	    String reqIssuer = reqClaims.getIssuer();
	    String dbIssuer = dbClaims.getIssuer();
	    String reqSub = reqClaims.getSubject();
	    String dbSub = dbClaims.getSubject();   
	    String reqId = reqClaims.getId();
	    String dbId = reqClaims.getId();

	    if (reqIssuer != null && dbIssuer != null && reqIssuer.equals(dbIssuer)) {       
	        System.out.println("ISSUE VALIDO");
	        if(reqSub != null && dbSub != null && reqSub.equals(dbSub)) {
	        	System.out.println("SUB VALIDO");
	        	if(reqId != null && dbId != null && reqId.equals(dbId)) {
	        		System.out.println("ID VALIDO");
	        		return true;
	        	}else {
	        		return false;
	        	}
	        }else {
	        	return false;
	        }
	    }else {
	    	return false;
	    	}
		}catch(NullPointerException | UnsupportedJwtException | MalformedJwtException | ExpiredJwtException | SignatureException | IllegalArgumentException e ) {
			return false;
		}
	}
}
