package kongkong.myrestfulservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kongkong.myrestfulservice.dao.UserDaoService;
import kongkong.myrestfulservice.domain.User;
import kongkong.myrestfulservice.exception.AllException;
import kongkong.myrestfulservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러")
public class UserController {

    private final UserDaoService service;

    @Operation(summary = "모든 사용자 정보 조회 API", description = "모든 사용자의 상세 정보를 조회합니다.")
    @ApiResponses({
           @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema)))
    })
    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<EntityModel<User>> retrieveUserById(@Parameter(description = "사용자 ID", required = true, example = "1") @PathVariable Long id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder webMvcLinkBuilder = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUsers());
        // all-users -> http://127.0.0.1:8080/users
        entityModel.add(webMvcLinkBuilder.withRel("all-users"));

        return ResponseEntity.ok(entityModel);
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
