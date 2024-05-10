package kongkong.myrestfulservice.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtTokenDto {

    private String refreshToken;
}
