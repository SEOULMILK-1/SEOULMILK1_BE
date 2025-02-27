package SeoulMilk1_BE.auth.controller.filter;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.JWT_AUTH_ERROR;

/**
 * 인증 되지 않은 사용자가 security 가 적용된 uri 에 액세스 할 때 호출(AccessToken 부정확 or 없음) - 401
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), ApiResponse.onFailure(JWT_AUTH_ERROR));
    }
}