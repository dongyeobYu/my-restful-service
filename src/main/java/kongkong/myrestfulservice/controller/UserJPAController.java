package kongkong.myrestfulservice.controller;

import jakarta.validation.Valid;
import kongkong.myrestfulservice.domain.Post;
import kongkong.myrestfulservice.domain.User;
import kongkong.myrestfulservice.domain.UserDto;
import kongkong.myrestfulservice.exception.UserNotFoundException;
import kongkong.myrestfulservice.repository.PostRepository;
import kongkong.myrestfulservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jpa")
@AllArgsConstructor
public class UserJPAController {

    private UserRepository userRepository;

    private PostRepository postRepository;

    @GetMapping("/allUsers")
    public ResponseEntity<HashMap<String, Object>> retrieveAllUsers(){

        HashMap<String, Object> hashMap = new HashMap<>();

        // count 추가
        hashMap.put("count", userRepository.count());
        hashMap.put("users", userRepository.findAll());

        return ResponseEntity.ok(hashMap);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> retrievePostById(@PathVariable Long id){
            return ResponseEntity.ok(postRepository.findById(id));
    }

    @GetMapping("/user/posts/{id}")
    public ResponseEntity<List<Post>> retrieveAllPostById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }

        return ResponseEntity.ok(user.get().getPosts());
    }

// TODO:: Custom Spring data JPA
//    @PostMapping("/users/post/{id}")
//    public ResponseEntity<List<Post>> retrievePostsByUserId(@PathVariable Long id){
//        List<Post> postList = postRepository.findPostByName(id);
//        return ResponseEntity.ok(postList);
//    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user){

        // TODO :: userDto 변환 Controller -> Service
        UserDto user1 = UserDto.from(userRepository.save(user));

        // URI 추가
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + user1.getId())
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).body(user1);
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

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable Long id){
        userRepository.deleteById(id);
    }
}
