package controller.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import java.sql.SQLException;
import java.util.Base64;
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
import io.jsonwebtoken.security.SignatureException;
import model.Dao;

public class JwtHandler {
Dao dao = new Dao();
private static String getSecretKey(String key) throws IOException {
	File f = new File("C:\\Users\\Joao Gabriel\\Desktop\\backend\\MiauDoteCao\\admin\\secretKey.ini");
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
		long expMillis = nowMillis + ttlMillis;
		Date exp = new Date(expMillis);
		builder.setExpiration(exp);
	}
	return builder.compact();
}
private static Claims decodeJWT(String jwt) throws SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException, IOException {
    Claims claims = Jwts.parser()
            .setSigningKey(Base64.getDecoder().decode(getSecretKey("secretKey")))
            .parseClaimsJws(jwt).getBody();
    return claims;
}

public boolean validateJWT(String jwt, String login, boolean isOng) throws ClassNotFoundException, IOException, SQLException {
	String dbJwt = dao.getJWT(login, isOng);
	Claims dbClaims = decodeJWT(dbJwt);
	Claims reqClaims = decodeJWT(jwt);
	if (dbClaims.getSubject().equals(reqClaims.getSubject()) 
	        && dbClaims.getId().equals(reqClaims.getId())
	        && dbClaims.getIssuer().equals(reqClaims.getIssuer())) {
				Date expiration = reqClaims.getExpiration();
				if(expiration != null && expiration.before(new Date())) {
					String newJwt = createJWT(dbClaims.getId(), dbClaims.getIssuer(), dbClaims.getSubject(), 3600000);
					dao.insertAndUpdateJWT(newJwt, isOng, login);
				}else {
					return false;
				}		
			return true;
	}else{
	    return false;
		}
	}
}
