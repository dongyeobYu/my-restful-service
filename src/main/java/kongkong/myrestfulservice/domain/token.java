package kongkong.myrestfulservice.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class token {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "ACCESS_TOKEN", length = 1000)
    private  String accesstoken;

    @Column(nullable = false, name = "REFRESH_TOKEN", length = 1000)
    private String refreshToken;

    private Date expiredDate;

    @Column(name = "EXPIRED_YN")
    private int expiredYN;

}
