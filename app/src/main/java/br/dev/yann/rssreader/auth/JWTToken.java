package br.dev.yann.rssreader.auth;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateful;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;

import br.dev.yann.rssreader.entity.User;

@Stateful
public class JWTToken {

  private static final String ISSUER = "Rss_Reader_App";
  private final byte[] SECRET = "JDMxJDE2JFp5NzNqNzItOTd5cjNLRm9DN0hXYVQtelRySlZJTEdMekhEVDZWeWxhV0E=".getBytes();

  public String getToken(User user) {

    try {

      long tenDaysFromNow = new Date().getTime() + TimeUnit.DAYS.toMillis(10);

      var header = new JWSHeader.Builder(JWSAlgorithm.HS256).build();
      var claims = new JWTClaimsSet.Builder()
                                    .issuer(ISSUER)
                                    .claim("usr", user.getId()+Integer.MAX_VALUE)
                                    .expirationTime(new Date(tenDaysFromNow))
                                    .build();

      var signer = new MACSigner(SECRET);

      var signedJWT = new SignedJWT(header, claims);

      signedJWT.sign(signer);

      return signedJWT.serialize();

    } catch (JOSEException e) {
      e.printStackTrace();
      return null;
    }
  }

  private boolean isTokenValid(String token, byte[] secret) throws ParseException, JOSEException {
    JWSObject jwsObject = JWSObject.parse(token);
    JWSVerifier verifier = new MACVerifier(secret);
    return jwsObject.verify(verifier);
  }

  public Map<String, Object> decode(String token) {

    try {
      JWTClaimsSet claims = JWTParser.parse(token).getJWTClaimsSet();

      if(claims.getExpirationTime() == null || claims.getExpirationTime().before(new Date())){
        return null;
      }

      if(claims.getIssuer() == null || !claims.getIssuer().equals(ISSUER)){
        return null;
      }

      Long id = claims.getLongClaim("usr");

      if(id == null){
        return null;
      } else{
        id -= Integer.MAX_VALUE;
      }

      var jsonObject  = claims.toJSONObject();

      if(jsonObject.size() != 3){
        return null;
      }


      if (isTokenValid(token, SECRET)) {
        jsonObject.put("usr",  id);
        return jsonObject;
      }

    } catch (ParseException | JOSEException e) {
      return null;
    }

    return null;
  }
}
