package kongkong.myrestfulservice.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import jakarta.validation.Valid;
import kongkong.myrestfulservice.dao.UserDaoService;
import kongkong.myrestfulservice.domain.AdminUser;
import kongkong.myrestfulservice.domain.User;
import kongkong.myrestfulservice.exception.AllException;
import kongkong.myrestfulservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService service;

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUserById(@PathVariable Long id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return user;
    }

    @GetMapping("/users/{id}/1")
    public User retrieveUserById1(@PathVariable Long id){
        User user = service.findOne(id);

        if(user == null){
            throw new AllException("Error");
        }

        return user;
    }

    /**
     *  매개변수로 받는 User 객체에 @Valid 를 선언해줘서 Validation 체크를 한다고 명시함. Validation 체크는 User 클래스에서 지정해줌 ex) @Size, @Past
     * */
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // PostMapping 으로 요청하는 id값
                .buildAndExpand(savedUser.getId()) // id값에 들어갈 값 -> 32번 라인에서 저장한 id
                .toUri(); // URI 로 변경

        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        User deleteUser = service.deleteById(id);

        if(deleteUser == null){
            throw new UserNotFoundException("User missed");
        }

        return ResponseEntity.noContent().build();
    }

}
