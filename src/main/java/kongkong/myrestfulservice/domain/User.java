package kongkong.myrestfulservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private Date joinDate;
}
