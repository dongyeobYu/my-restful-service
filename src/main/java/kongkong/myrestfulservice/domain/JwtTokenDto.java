package kongkong.myrestfulservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtTokenDto {

    private String refreshToken;
}
