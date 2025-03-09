package SeoulMilk1_BE.post.dto.response.post;

import SeoulMilk1_BE.post.dto.response.comment.CommentReadResponse;
import SeoulMilk1_BE.user.domain.type.Role;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailResponse(Long postId, String userName, Role role, String title, String content, Long views, List<String> imgUrls, LocalDateTime updatedAt, List<CommentReadResponse> comments, Boolean pin) {
    public static PostDetailResponse of(Long postId, String userName, Role role, String title, String content, Long views, List<String> imgUrls, LocalDateTime updatedAt, List<CommentReadResponse> comments, Boolean pin) {
        return new PostDetailResponse(postId, userName, role, title, content, views, imgUrls, updatedAt, comments, pin);
    }
}
