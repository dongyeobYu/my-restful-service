package kongkong.myrestfulservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthResponse {

    private final String accessToken;
    private final String refreshToken;
}
