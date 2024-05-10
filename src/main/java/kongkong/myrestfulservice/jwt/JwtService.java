package kongkong.myrestfulservice.jwt;

import kongkong.myrestfulservice.domain.AuthRequest;
import kongkong.myrestfulservice.domain.AuthResponse;
import kongkong.myrestfulservice.jwt.redis.RedisRepository;
import kongkong.myrestfulservice.jwt.redis.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtService {

    private final JwtRepository jwtRepository;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final RedisRepository redisRepository;

    @Transactional
    public AuthResponse createToken(AuthRequest authRequest){

        // 입력받은 로그인 정보로 UsernamePasswordAuthenticationToken 생성
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));

        // authentication 객체에서 UserDetails 추출(사용자의 상세정보 포함)
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // JWT 토큰 생성
        final String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
        final String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());

        redisRepository.save(new RefreshToken(refreshToken, userDetails.getUsername()));

        return new AuthResponse(accessToken, refreshToken);
    }

    /*@Transactional
    public void saveToken(String accessToken, String refreshToken){

        tokenDto tokenDto = kongkong.myrestfulservice.jwt.tokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredYN(1)
                .expiredDate(jwtUtil.extractExpiredDate(refreshToken))
                .build();

        jwtRepository.save(tokenDto.toEntity(tokenDto));
    }*/

    @Transactional
    public boolean checkToken(String refreshToken){
        token token = jwtRepository.findByRefreshToken(refreshToken);

        return token.getExpiredYN() != 0;
    }

    @Transactional
    public void expriedToken(String refreshToken){
        jwtRepository.updateExpiredYNByToken(refreshToken);
    }

}
