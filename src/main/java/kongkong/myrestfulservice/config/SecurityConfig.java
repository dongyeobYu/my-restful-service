//package kongkong.myrestfulservice.config;
//
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    @ConditionalOnProperty(name = "spring.h2.console.enalbed", havingValue = "true")
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return web -> web.ignoring().requestMatchers(PathRequest.toH2Console());
//    }
//
//    /*@Bean
//    UserDetailsService userDetailsService(){
//
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
//
//        *//*UserDetails userDetails = User.builder()
//                .username("user")
//                .password(bCryptPasswordEncoder().encode("password"))
//                .authorities("USER")
//                .build();*//*
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
//*/
//
//    @Bean
//    BCryptPasswordEncoder bCryptPasswordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
