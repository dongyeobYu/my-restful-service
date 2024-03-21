package kongkong.myrestfulservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Builder
@JsonFilter("userInfo")
public class AdminUser {

    private Long id;

    @Size(min = 2, message = "Name 은 2글자 이상 입력해 주세요")
    private String name;

    @Past(message = "등록일은 과거 날짜만 입력하실 수 있습니다.")
    private Date joinDate;

    //@JsonIgnore     // JSON 결과값이 포함하지 않음.
    private String password;

    //@JsonIgnore
    private String ssn;


    // 복사 팩토리 메서드
    public static AdminUser copyUser(User user){
        return AdminUser.builder()
                .id(user.getId())
                .password(user.getPassword())
                .joinDate(user.getJoinDate())
                .ssn(user.getSsn())
                .name(user.getName())
                .build();
    }

}
