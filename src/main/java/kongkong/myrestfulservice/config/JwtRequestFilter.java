package kongkong.myrestfulservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtRequestFilter  extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String filterSkipPath = request.getRequestURI();

        // 로그인, 회원가입은 필터적용 X
        if(filterSkipPath.startsWith("/jpa/users/login") || filterSkipPath.startsWith("/jpa/createUser")){
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");        // Header 에서 Authorization 정보를 가져옴

        String token = null;
        String username = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){       // Authorization 이 Null 이 아니고 Bearer로 시작하면 Jwt 토큰, 만료시간이 안지났으면
            token = authorizationHeader.substring(7);                              // 토큰값 가져오기
            username = jwtUtil.extractUsername(token);                                      // 토큰에서 Username 추출하기
        } else{
            // Bearer 타입이 아니거나, 토큰이 없으면 401 Unauthorized 응답
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        if(username != null & SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if(jwtUtil.validateToken(token)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else{
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

        }

        filterChain.doFilter(request, response);

    }
}
