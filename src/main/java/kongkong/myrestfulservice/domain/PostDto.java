package kongkong.myrestfulservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostDto {

    private final Long id;

    private final String description;
    private final Long userId;

    private final List<Post> posts;

    @Builder
    public PostDto(Long id, String description, Long userId, List<Post> posts) {
        this.id = id;
        this.description = description;
        this.userId = userId;
        this.posts = posts;
    }
}
