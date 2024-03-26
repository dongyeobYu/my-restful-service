package kongkong.myrestfulservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Getter
@SuperBuilder
@JsonFilter("userInfo")
public class AdminUser {

    private Long id;

    @Size(min = 2, message = "Name 은 2글자 이상 입력해 주세요")
    private String name;

    @Past(message = "등록일은 과거 날짜만 입력하실 수 있습니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd HH:MM:SS")
    private LocalDateTime joinDate;

    //@JsonIgnore     // JSON 결과값이 포함하지 않음.
    private String password;

    //@JsonIgnore
    private String ssn;


    // 복사 팩토리 메서드
    public static AdminUser copyUser(User user) {
        return AdminUser.builder()
                .id(user.getId())
                .password(user.getPassword())
                .joinDate(user.getJoinDate())
                .ssn(user.getSsn())
                .name(user.getName())
                .build();
    }

    // 복사 팩토리 메서드 -> Stream().map() 사용
    // userList 를 가져와서 각각 AdminUser로 객체 변환 후 리스트로 반환
    // Collectors -> 원하는 자료형으로 변환
    // Collectors.toList() -> 리스트로 변환
    public static List<AdminUser> copyUserList(List<User> userList) {
        return userList.stream().map(
                        user -> AdminUser.builder()
                                .id(user.getId())
                                .password(user.getPassword())
                                .joinDate(user.getJoinDate())
                                .ssn(user.getSsn())
                                .name(user.getName())
                                .build())
                .collect(Collectors.toList());
    }
}
