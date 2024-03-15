package kongkong.myrestfulservice.domain;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
public class User {

    private Long id;

    @Size(min = 2, message = "Name 은 2글자 이상 입력해 주세요")
    private String name;

    @Past(message = "등록일은 과거 날짜만 입력하실 수 있습니다.")
    private Date joinDate;

    @Builder
    public User(Long id, String name, Date joinDate){
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
    }

}
