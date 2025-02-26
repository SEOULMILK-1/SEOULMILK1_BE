package SeoulMilk1_BE.post.dto.response.comment;

import java.time.LocalDateTime;

public record CommentCreateResponse(Long commentId, LocalDateTime createdAt) {
}
