package kongkong.myrestfulservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import kongkong.myrestfulservice.domain.role.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@JsonIgnoreProperties(value = {"password", "ssn"}, allowSetters = true)  // 외부에 노출하기 싫은 데이터 제외
@Schema(description = "사용자 상세 정보를 위한 도메인 객체")   // Swaggger 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class User {

    @Schema(title = "사용자 ID", description = "사용자 아이디입니다.")
    @Id
    @GeneratedValue
    private Long id;

    @Schema(title = "사용자 이름", description = "사용자 이름입니다.")
    @Size(min = 2, message = "Name 은 2글자 이상 입력해 주세요")
    private String name;

    @Schema(title = "등록일", description = "사용자의 등록일입니다.")
    @Past(message = "등록일은 과거 날짜만 입력하실 수 있습니다.")
    @JsonFormat(pattern = "yyyy-mm-dd HH:MM:SS")
    private LocalDateTime joinDate;

    @Schema(title = "비밀번호", description = "사용자 비밀번호입니다.")
    private String password;

    @Schema(title = "주민번호", description = "사용자 주민등록번호입니다.")
    //@JsonIgnore
    private String ssn;

    @Schema(title = "권한", description = "사용자 권한입니다.")
    private Enum<Role> role;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @Builder
    public User(Long id, String name, LocalDateTime joinDate, String password, String ssn, Enum<Role> role) {
        this.id = id;
        this.name = name;
        this.joinDate = joinDate;
        this.password = password;
        this.ssn = ssn;
        this.role = role;
    }
}
