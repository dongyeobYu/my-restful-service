//package kongkong.myrestfulservice.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
////@Configuration
////@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    UserDetailsService userDetailsService(){
//
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//
//        /*UserDetails userDetails = User.builder()
//                .username("user")
//                .password(bCryptPasswordEncoder().encode("password"))
//                .authorities("USER")
//                .build();*/
//
//        UserDetails userDetails = User.withUsername("user")
//                                    .password(bCryptPasswordEncoder().encode("password"))
//                                    .authorities("USER")
//                                    .build();
//
//        userDetailsManager.createUser(userDetails);
//
//        return userDetailsManager;
//    }
//
//
//    @Bean
//    BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
