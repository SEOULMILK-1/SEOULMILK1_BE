package SeoulMilk1_BE.auth.service.type;

import SeoulMilk1_BE.user.domain.type.Role;
import io.jsonwebtoken.Claims;
import lombok.Builder;

@Builder
public record JwtUserDetails(
        Long userId,
        Role role
) {
    public static JwtUserDetails fromClaim(Claims claims) {
        String roleString = claims.get("role").toString();
        Role role = Role.valueOf(roleString.replace("ROLE_", "")); // "ROLE_ADMIN" -> "ADMIN"

        return JwtUserDetails.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .role(role)
                .build();
    }
}