package com.learn.electronic.store.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


//this class is use for jwt opertions like generate jwt token

@Component
public class JwtHelper {

    //requirement :
    public static final long TOKEN_VALIDITY = 5 * 60 * 60*1000;

    //    public static final long JWT_TOKEN_VALIDITY =  60;
    private String SECRET_KEY= "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY ))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }

    //validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


}



//@Component
//public class JwtHelper {
//
//    //requirement
//
//    //              1. Validity
//    // validity in millis
//    public static final long TOKEN_VALIDITY= (5*60*60*1000);     //HOURS*MINUTES*SECONDS*1000
//
//
//    //             2. Secret Key
//    public static  final String SECRET_KEY="fdjgvdjcvhbdfnfgyhjktfyghjkfdyugdfusdfuhsdjfhjsdfsjdhfjkshdkjfksdjfvhbdnvhbjdcxvhbjdxhjzcjsdkziweujtoiureieowpdsjchgjdkfscnhvujtgfvvghjuy";
//
//
//    //retrieve username from jwt token
//    public String getUsernameFromToken(String token){
//        return getClaimFromToken(token,Claims::getSubject);
//    }
//
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
////        System.out.println(claimsResolver.apply(claims));
//        return claimsResolver.apply(claims);
//    }
//
//    //for retrieveing any information from token we will need the secret key
//    public Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
//    }
//
//    //check if the token has expired
//    public Boolean isTokenExpired(String token){
//        final Date expiration=getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//
//    //retrieve expiration date from jwt token
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    //generate token for user
//    public String generateToken(UserDetails userDetails){
//        Map<String,Object> claims=new HashMap<>();
//        return doGenerateToken(claims,userDetails.getUsername());
//    }
//
//    private String doGenerateToken(Map<String,Object> claims,String subject){
//        return
//                Jwts.builder()
//                        .setClaims(claims)
//                        .setSubject(subject)
//                        .setIssuedAt(new Date(System.currentTimeMillis()*TOKEN_VALIDITY))
//                        .signWith(SignatureAlgorithm.HS512,SECRET_KEY).compact();
//    }
//
//}
