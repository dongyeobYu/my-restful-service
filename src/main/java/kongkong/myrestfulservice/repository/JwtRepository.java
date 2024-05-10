package kongkong.myrestfulservice.repository;

import kongkong.myrestfulservice.domain.token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends JpaRepository<token, Long> {

    token findByRefreshToken(String token);

    @Modifying
    @Query("update token t set t.expiredYN = 0 where t.refreshToken = :token")
    void updateExpiredYNByToken(@Param("token") String token);
}
