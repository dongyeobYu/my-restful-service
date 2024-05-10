package kongkong.myrestfulservice.jwt.redis;

import org.springframework.data.repository.CrudRepository;

public interface RedisRepository extends CrudRepository<RefreshToken, String> {
}
