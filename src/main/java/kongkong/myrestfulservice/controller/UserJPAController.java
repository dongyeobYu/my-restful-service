package kongkong.myrestfulservice.controller;

import jakarta.validation.Valid;
import kongkong.myrestfulservice.domain.User;
import kongkong.myrestfulservice.domain.UserDto;
import kongkong.myrestfulservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jpa")
@AllArgsConstructor
public class UserJPAController {

    private UserRepository userRepository;

    @GetMapping("/allUsers")
    public ResponseEntity<List<User>> retrieveAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user){

        User user1 = userRepository.save(user);

        return ResponseEntity.ok(UserDto.from(user1));
    }

}
