package kongkong.myrestfulservice.repository;

import kongkong.myrestfulservice.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
