package kongkong.myrestfulservice.jwt;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class tokenDto {

    @Column(length = 1000)
    private final String accessToken;

    @Column(length = 1000)
    private final String refreshToken;

    private final int expiredYN;

    private final Date expiredDate;

    public token toEntity(tokenDto tokenDto) {
        return new token(null, tokenDto.accessToken, tokenDto.refreshToken, tokenDto.expiredDate, tokenDto.expiredYN);

    }
}
