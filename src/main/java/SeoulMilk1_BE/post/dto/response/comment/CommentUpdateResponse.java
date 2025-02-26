package SeoulMilk1_BE.post.dto.response.comment;

import java.time.LocalDateTime;

public record CommentUpdateResponse(Long commentId, LocalDateTime updatedAt) {
    public static CommentUpdateResponse from(Long commentId, LocalDateTime updatedAt) {
        return new CommentUpdateResponse(commentId, updatedAt);
    }
}
