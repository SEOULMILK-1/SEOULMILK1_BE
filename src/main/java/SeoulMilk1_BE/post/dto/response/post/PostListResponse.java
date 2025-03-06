package SeoulMilk1_BE.post.dto.response.post;

import SeoulMilk1_BE.user.domain.type.Role;

public record PostListResponse(Long postId, String title, String username, Role role) {
    public static PostListResponse from(Long postId, String title, String username, Role role) {
        return new PostListResponse(postId, title, username, role);
    }
}
