package kongkong.myrestfulservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@JsonIgnoreProperties({"password", "ssn"})  // 외부에 노출하기 싫은 데이터 제외
public class User {

    private Long id;

    @Size(min = 2, message = "Name 은 2글자 이상 입력해 주세요")
    private String name;

    @Past(message = "등록일은 과거 날짜만 입력하실 수 있습니다.")
    private Date joinDate;

    //@JsonIgnore     // JSON 결과값이 포함하지 않음.
    private String password;

    //@JsonIgnore
    private String ssn;



}
