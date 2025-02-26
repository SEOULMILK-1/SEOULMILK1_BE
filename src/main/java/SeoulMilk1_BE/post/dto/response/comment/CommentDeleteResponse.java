package SeoulMilk1_BE.post.dto.response.comment;

import java.time.LocalDateTime;

public record CommentDeleteResponse(Long commentId, LocalDateTime deletedAt) {
}
