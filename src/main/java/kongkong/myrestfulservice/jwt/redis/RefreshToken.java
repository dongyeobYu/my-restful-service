package kongkong.myrestfulservice.jwt.redis;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 259200)
@RequiredArgsConstructor
public class RefreshToken {

    @Id // org.springframework.data.annotation.Id;      // @Id 어노테이션을 붙혀준 값이 Key
    private final String refreshToken;

    private final String username;
}
