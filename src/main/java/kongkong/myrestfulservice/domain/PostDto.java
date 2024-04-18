package kongkong.myrestfulservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private final Long id;

    private final String description;
    private final Long userId;

    @Builder
    public PostDto(Long id, String description, Long userId) {
        this.id = id;
        this.description = description;
        this.userId = userId;
    }
}
