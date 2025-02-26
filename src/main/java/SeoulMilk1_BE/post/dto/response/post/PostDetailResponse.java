package SeoulMilk1_BE.post.dto.response.post;

import SeoulMilk1_BE.post.domain.type.Type;
import SeoulMilk1_BE.post.dto.response.comment.CommentReadResponse;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailResponse(Long postId, String userName, String title, String content, Type type, Long views, List<String> imgUrls, LocalDateTime createdAt, LocalDateTime updatedAt, List<CommentReadResponse> comments) {
}
