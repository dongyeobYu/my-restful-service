package kongkong.myrestfulservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

// 해당 애플리케이션에서 전역적으로 예외를 핸들링하는 어노테이션
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception 예외에 관한 핸들링
     * */
    //@ExceptionHandler({Exception.class})
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        request.getDescription(false),
                        "About All Exception"
                );

        /**
         *  excetpionResponse - 정보, HttpStatus - 에러코드 (500)
         * */
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     *  UserNotFoundException 예외에 관한 핸들링
     * */
    @ExceptionHandler({UserNotFoundException.class})
    public final ResponseEntity<Object> handleUserNotFoundException(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        ex.getMessage(),
                        request.getDescription(false),
                        "About UserNotFoundException"
                );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionResponse exceptionResponse =
                new ExceptionResponse(
                        new Date(),
                        "Validation Failed",
                        ex.getBindingResult().toString(),
                        "Valid Exception"
                );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
