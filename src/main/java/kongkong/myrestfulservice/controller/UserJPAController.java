package kongkong.myrestfulservice.controller;

import jakarta.validation.Valid;
import kongkong.myrestfulservice.config.JwtUtil;
import kongkong.myrestfulservice.domain.*;
import kongkong.myrestfulservice.exception.UserNotFoundException;
import kongkong.myrestfulservice.repository.PostRepository;
import kongkong.myrestfulservice.repository.UserRepository;
import kongkong.myrestfulservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    private UserService userService;

    private PostRepository postRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public ResponseEntity<PostDto> retrieveAllPostById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }

        // TODO::순환참조 발생. 수정필요
        List<Post> postList = user.get().getPosts();

        PostDto postDto = PostDto.builder().id(id).userId(user.get().getId()).build();

        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/v2/user/posts/{id}")
    public ResponseEntity<PostDto> retrieveAllPostByIdV2(@PathVariable Long id) {
        Optional<Post> posts = postRepository.findById(id);

        // Exception 처리 필요
        /*if(posts.isEmpty()){
            throw new UserNotFoundException("11");
        }*/

        PostDto postDto = PostDto.builder()
                .id(id)
                .userId(posts.get().getUser().getId())
                .description(posts.get().getDescription())
                .build();

        return ResponseEntity.ok(postDto);
    }

// TODO:: Custom Spring data JPA
//    @PostMapping("/users/post/{id}")
//    public ResponseEntity<List<Post>> retrievePostsByUserId(@PathVariable Long id){
//        List<Post> postList = postRepository.findPostByName(id);
//        return ResponseEntity.ok(postList);
//    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

        // TODO :: userDto 변환 Controller -> Service
        UserDto user1 = userService.save(userDto);

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

    @PostMapping("/users/posts/{id}")
    public ResponseEntity<Post> createPost(@PathVariable long id, @RequestBody Post post){
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }

        post.setUser(user.get());

        postRepository.save(post);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()       // 
                .path("/{id}")
                .buildAndExpand(post.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping(value = "/users/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        // 입력받은 로그인 정보로 UsernamePasswordAuthenticationToken 생성
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getName(), authRequest.getPassword()));

        // authentication 객체에서 UserDetails 추출(사용자의 상세정보 포함)
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        
        // JWT 토큰 생성
        final String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
        final String refreshToken = jwtUtil.generateRefreshToken(accessToken);
        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken));
    }

    // 리프레시 토큰
    @PostMapping("/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody JwtTokenDto jwtTokenDto) {

        String refreshToken = jwtTokenDto.getRefreshToken();

        // 만료되었는지 확인
        if (jwtUtil.isTokenExpired(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token is expired");
        }

        String username = jwtUtil.extractUsername(refreshToken);

        // refreshToken 의 Subject 와 추출한 유저가 동일한지 확인
        if(jwtUtil.validateToken(refreshToken, username)){

            
            // 동일하면 새로운 AccessToken, RefreshToken 발급
            String newAccessToken = jwtUtil.generateAccessToken(username);
            String newRefreshToken = jwtUtil.generateRefreshToken(username);
            return ResponseEntity.ok(new AuthResponse(newAccessToken, newRefreshToken));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
    }
}
