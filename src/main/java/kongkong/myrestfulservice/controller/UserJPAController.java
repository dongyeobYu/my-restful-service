package kongkong.myrestfulservice.controller;

import jakarta.validation.Valid;
import kongkong.myrestfulservice.domain.User;
import kongkong.myrestfulservice.domain.UserDto;
import kongkong.myrestfulservice.exception.UserNotFoundException;
import kongkong.myrestfulservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

        UserDto user1 = UserDto.from(userRepository.save(user));

        return ResponseEntity.ok(user1);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<UserDto>> retrieveUser(@PathVariable Long id){
        Optional<User> member = userRepository.findById(id);


        if(member.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }

        // DTO 로 변환
        UserDto userDto = UserDto.from(member.get());

        // EntityModel 에 Member 값 담기
        EntityModel<UserDto> entityModel = EntityModel.of(userDto);

        // EntityModel 에 담긴 값에 link 추가하기
        WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());

        // link 객체 이름 정하기
        entityModel.add(linkTo.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
    }
}
