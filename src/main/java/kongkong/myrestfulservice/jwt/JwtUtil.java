package kongkong.myrestfulservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "KEYKEYKEY";

    /***
     * Access 토큰 생성
     * @param username 유저 ID 
     * @return 생성된 JWT token 문자열 반환
     */
    public String generateAccessToken(String username) {
        return JWT.create()
                .withSubject(username)                                                      // 토큰 주체, 사용자 이름
                .withIssuedAt(new Date())                                                   // 토큰 발행 시간, 현재시간
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))       // 1 hours 1000 * 60
                .withClaim("token_type", "access")                         // token_type 추가 , claim 커스텀
                .sign(Algorithm.HMAC256(SECRET_KEY));                                       // 서명, SECRET_KEY 를 HMAC256 알고리즘으로 변환
    }

    /***
     *  Refresh 토큰 생성
     * @param username 유저 ID
     * @return 생성된 JWT token 문자열 반환
     */
    public String generateRefreshToken(String username) {

        return JWT.create()
                .withSubject(username)                                                      // 토큰 주체, 사용자 이름
                .withIssuedAt(new Date())                                                   // 토큰 발행 시간, 현재시간
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3))       // 3 day 1000 * 60 * 60 * 24 * 3
                .withClaim("token_type", "refresh")                         // token_type 추가 , claim 커스텀
                .sign(Algorithm.HMAC256(SECRET_KEY));                                       // 서명, SECRET_KEY 를 HMAC256 알고리즘으로 변환
    }

    /**
     * 토큰 추출
     * @param token 토큰
     * @return 토큰에서 추출한 ID
     * */
    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    /**
     * 토큰 추출
     * @param token 토큰
     * @return 토큰에서 추출한 타입 ex)access, refresh
     * */
    public String extractType(String token) {
        return JWT.decode(token).getClaim("token_type").toString();
    }

    /**
     *  토큰 검증
     * @param token 토큰
     * @return boolean
     */
    public boolean validateToken(String token, String name){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                                      .withSubject(name)
                                      .build();
            DecodedJWT jwt = verifier.verify(token);

            String tokenSubject = jwt.getSubject();
            return tokenSubject.equals(name);
        } catch(JWTVerificationException e){
            // Invalid Token
            //e.printStackTrace();
        }

        return false;
    }


}
