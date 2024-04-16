package kongkong.myrestfulservice.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
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
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/", "/login/**").permitAll()
                        //.requestMatchers("/jpa/posts/*").hasRole(Role.USER.getRole())
                        //.requestMatchers("/admin/**").hasRole(Role.ADMIN.getRole())
                        .anyRequest().permitAll()
                )
                //X-Frame-Options 브라우저에서 iframe 에서 일어난 요청에 대해 Origin 을 파악하고 같으면 요청을 허용하게됨
                //Spring Security 는 기본적으로 X-Frame-Options 에서 Click jacking 을 막고있음
                //*click jacking -> 해킹 기법
                .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .exceptionHandling((exception) -> {
                            //TODO :: Exception Handler
                        }
                );

        return httpSecurity.build();
    }
}
