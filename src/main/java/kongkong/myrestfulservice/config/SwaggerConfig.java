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


    /**
     * Swagger 3 annotations
     *
     * @Tag                                                     -> 클래스에 설명, Swagger 리소스
     * @Parameter                                               -> API 에서의 단일 매개 변수
     * @Parameters                                              -> API 에서의 복수 매개 변수
     * @Schema                                                  -> Swagger 모델에 대한 추가 정보
     * @Operation(summary="foo", description = "Bar")           -> 특정 경로에 대한 작업 (일반적으로 컨트롤러에서의 HTTP 메서드를 설명)
     * @ApiResponse(responseCode = "404", description = "Foo")  -> API 에서의 작업 처리에 대한 응답코드 설명
     *
     * */
}
