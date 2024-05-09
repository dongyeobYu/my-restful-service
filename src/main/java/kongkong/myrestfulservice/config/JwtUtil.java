package kongkong.myrestfulservice.config;

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
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 30))       // 1 hours 1000 * 60 * 60
                .withClaim("token_type", "access token")                         // token_type 추가 , claim 커스텀
                .sign(Algorithm.HMAC256(SECRET_KEY));                                       // 서명, SECRET_KEY 를 HMAC256 알고리즘으로 변환
    }

    /***
     *  Refresh 토큰 생성
     * @param token JWT 토큰
     * @return 생성된 JWT token 문자열 반환
     */
    public String generateRefreshToken(String token) {

        String username = this.extractUsername(token);

        return JWT.create()
                .withSubject(username)                                                      // 토큰 주체, 사용자 이름
                .withIssuedAt(new Date())                                                   // 토큰 발행 시간, 현재시간
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3))       // 3 day
                .withClaim("token_type", "refresh token")                         // token_type 추가 , claim 커스텀
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
     * 토큰이 만료되었는지 확인
     * @param token 토큰
     * @return 토큰에서 추출한 만료시간
     * */
    public Boolean isTokenExpired(String token){

        final Date expricateion = JWT.decode(token).getExpiresAt();             // 만료시간 추출
        return expricateion.before(new Date());                                  // 만료되었으면 true, 아니면 false
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
