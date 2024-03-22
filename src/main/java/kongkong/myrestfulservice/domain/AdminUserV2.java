package kongkong.myrestfulservice.domain;

import com.fasterxml.jackson.annotation.JsonFilter;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@SuperBuilder
@JsonFilter("userInfoV2")
public class AdminUserV2 extends AdminUser {

    private String grade;


    // 복사 팩토리 메서드
    public static AdminUserV2 copyUser(User user, String grade) {
        return AdminUserV2.builder()
                .id(user.getId())
                .password(user.getPassword())
                .joinDate(user.getJoinDate())
                .ssn(user.getSsn())
                .name(user.getName())
                .grade(grade)
                .build();
    }

    // 복사 팩토리 메서드 -> Stream().map() 사용
    // userList 를 가져와서 각각 AdminUser로 객체 변환 후 리스트로 반환
    // Collectors -> 원하는 자료형으로 변환
    // Collectors.toList() -> 리스트로 변환
    public static List<AdminUserV2> copyUserList(List<User> userList, String grade) {
        return userList.stream().map(
                        user -> AdminUserV2.builder()
                                .id(user.getId())
                                .password(user.getPassword())
                                .joinDate(user.getJoinDate())
                                .ssn(user.getSsn())
                                .name(user.getName())
                                .grade(grade)
                                .build())
                .collect(Collectors.toList());
    }
}
