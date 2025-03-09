package SeoulMilk1_BE.post.dto.response.post;

import SeoulMilk1_BE.user.domain.type.Role;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostListResponse(
        Long postId,
        String title,
        String username,
        Role role,
        Boolean pin,
        LocalDateTime modifiedAt) {
    public static PostListResponse from(Long postId, String title, String username, Role role, Boolean pin, LocalDateTime modifiedAt) {
        return PostListResponse.builder()
                .postId(postId)
                .title(title)
                .username(username)
                .role(role)
                .pin(pin)
                .modifiedAt(modifiedAt)
                .build();
    }
}
