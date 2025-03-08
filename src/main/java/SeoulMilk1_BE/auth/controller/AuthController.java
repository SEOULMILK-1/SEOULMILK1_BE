package SeoulMilk1_BE.auth.controller;

import SeoulMilk1_BE.auth.dto.request.LoginRequest;
import SeoulMilk1_BE.auth.dto.request.SignUpCSRequest;
import SeoulMilk1_BE.auth.dto.request.SignUpHQRequest;
import SeoulMilk1_BE.auth.dto.response.LoginResponse;
import SeoulMilk1_BE.auth.dto.response.SearchCsNameResponseList;
import SeoulMilk1_BE.auth.service.AuthService;
import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "Auth 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "테스트용 토큰발급", description = "테스트용 토큰발급 (관리자 : 1, 본사 : 2, 대리점 : 3)")
    @GetMapping("/test/{userId}")
    public String testToken(@PathVariable Long userId) {
        return authService.getTestToken(userId);
    }

    @Operation(summary = "아이디 중복 검사", description = "아이디 중복 검사")
    @GetMapping("/validation/login-id")
    public ApiResponse<?> validateEmployeeId(@Valid @RequestParam String loginId) {
        return ApiResponse.onSuccess(authService.validateLoginId(loginId));
    }

    @Operation(summary = "모든 대리점명 조회", description = "모든 대리점 이름을 리스트로 제공합니다.")
    @GetMapping("/search/cs")
    public ApiResponse<SearchCsNameResponseList> searchCs() {
        return ApiResponse.onSuccess(authService.searchCsName());
    }

    @Operation(summary = "본사 회원가입", description = "본사 회원가입 API입니다.")
    @PostMapping("/sign-up/hq")
    public ApiResponse<?> signUp(@Valid @RequestBody SignUpHQRequest request) {
        return ApiResponse.onSuccess(authService.signUpHQ(request));
    }

    @Operation(summary = "대리점 회원가입", description = "대리점 회원가입 API입니다.")
    @PostMapping("/sign-up/cs")
    public ApiResponse<?> signUp(@Valid @RequestBody SignUpCSRequest request) {
        return ApiResponse.onSuccess(authService.signUpCS(request));
    }

    @Operation(summary = "로그인", description = "access token은 header에 Authorization: Bearer 로, refresh token은 쿠키로 전달됩니다. <br><br>" +
            "이후 user의 role에 맞춰 redirect를 해주시면 됩니다.")
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletResponse response) {
        return ApiResponse.onSuccess(authService.login(loginRequest, response));
    }
}