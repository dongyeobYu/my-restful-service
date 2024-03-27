package kongkong.myrestfulservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Restful service API 명세서",
        description = "Spring Boot 로 개발하는 Restful API 명세서 입니다.",
        version = "1.0.0"))
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi customTestOpenAPI(){
        
        // 보여주고 싶은 경로
        String[] paths = {"/users/**", "/admin/**"};

        return GroupedOpenApi.builder()
                .group("일반 사용자 및 관리자를 위한 페이지 입니다.") // 그룹 정보
                .pathsToMatch(paths)
                .build();
    }
}
