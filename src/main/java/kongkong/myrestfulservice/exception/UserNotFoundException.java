package kongkong.myrestfulservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// @ResponseStatus(HttpStatus.NOT_FOUND) 해당 클래스가 사용 될 시 NOT_FOUND STATUS 반환
@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String msg){
         super(msg);
    }
}
