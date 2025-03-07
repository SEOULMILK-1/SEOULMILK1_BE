package SeoulMilk1_BE.post.dto.response.post;

import SeoulMilk1_BE.user.domain.type.Role;

import java.time.LocalDateTime;

public record PostListResponse(Long postId, String title, String username, Role role, Boolean pin, LocalDateTime modifiedAt) {
    public static PostListResponse from(Long postId, String title, String username, Role role, Boolean pin, LocalDateTime modifiedAt) {
        return new PostListResponse(postId, title, username, role, pin, modifiedAt);
    }
}
