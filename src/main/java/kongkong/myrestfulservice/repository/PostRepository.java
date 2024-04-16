package kongkong.myrestfulservice.repository;

import kongkong.myrestfulservice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    //List<Post> findPostByName(Long name);
}
