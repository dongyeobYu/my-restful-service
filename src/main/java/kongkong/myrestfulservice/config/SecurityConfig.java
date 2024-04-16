package kongkong.myrestfulservice.config;

import kongkong.myrestfulservice.domain.role.Role;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enalbed", havingValue = "true")
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(PathRequest.toH2Console());
    }

    /*@Bean
    UserDetailsService userDetailsService(){

        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();

        *//*UserDetails userDetails = User.builder()
                .username("user")
                .password(bCryptPasswordEncoder().encode("password"))
                .authorities("USER")
                .build();*//*

        UserDetails userDetails = User.withUsername("user")
                                    .password(bCryptPasswordEncoder().encode("password"))
                                    .authorities("USER")
                                    .build();

        userDetailsManager.createUser(userDetails);

        return userDetailsManager;
    }
*/

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, HandlerMappingIntrospector introspector) throws Exception {

        // 예전은             httpSecurity.csrf().disable()
        // 스프링 부트 3 이후는 httpSecurity.csrf(AbstractHttpConfigurer::disable)

        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/", "/login/**").permitAll()
                        .requestMatchers("/jpa/posts/*").hasRole(Role.USER.getRole())
                        .requestMatchers("/admin/**").hasRole(Role.ADMIN.getRole())
                        .anyRequest().permitAll()

                )
                .exceptionHandling((exception) -> {
                            //TODO :: Exception Handler
                        }
                );

        return httpSecurity.build();
    }
}
