package kongkong.myrestfulservice.controller;

import kongkong.myrestfulservice.dao.UserDaoService;
import kongkong.myrestfulservice.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        return service.findOne(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}") // PostMapping 으로 요청하는 id값
                .buildAndExpand(savedUser.getId()) // id값에 들어갈 값 -> 32번 라인에서 저장한 id
                .toUri(); // URI 로 변경

        return ResponseEntity.created(location).build();
    }
}
