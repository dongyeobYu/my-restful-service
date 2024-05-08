package kongkong.myrestfulservice.domain;

import kongkong.myrestfulservice.domain.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


// TODO : dto -> record 클래스로 변경

@AllArgsConstructor
@Getter
@Setter
public class UserDto {

    private final Long id;
    private final String name;
    private final String password;
    private final Role role;


    @Builder
    public static UserDto from(User user){
        return new UserDto(user.getId(), user.getName(), user.getPassword(), user.getRole());
    }

}
