package SeoulMilk1_BE.post.dto.response;

import java.time.LocalDateTime;

public record CommentCreateResponse(Long commentId, LocalDateTime createdAt) {
}
