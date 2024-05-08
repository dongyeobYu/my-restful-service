package kongkong.myrestfulservice.service;

import kongkong.myrestfulservice.domain.CustomUserDetails;
import kongkong.myrestfulservice.domain.User;
import kongkong.myrestfulservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByName(username);

        User userInfo = user.get();

        return new CustomUserDetails(userInfo);
    }
}
