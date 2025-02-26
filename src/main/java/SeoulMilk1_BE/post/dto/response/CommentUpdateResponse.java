package SeoulMilk1_BE.post.dto.response;

import java.time.LocalDateTime;

public record CommentUpdateResponse(Long commentId, LocalDateTime updatedAt) {
}
