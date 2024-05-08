package kongkong.myrestfulservice.service;

import kongkong.myrestfulservice.domain.User;
import kongkong.myrestfulservice.domain.UserDto;
import kongkong.myrestfulservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto save(UserDto userDto){
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();

        return UserDto.from(userRepository.save(user));
    }
}
