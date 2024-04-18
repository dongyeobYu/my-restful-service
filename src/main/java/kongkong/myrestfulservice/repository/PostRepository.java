package kongkong.myrestfulservice.repository;

import kongkong.myrestfulservice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findPostByUserId(Long name);
}
