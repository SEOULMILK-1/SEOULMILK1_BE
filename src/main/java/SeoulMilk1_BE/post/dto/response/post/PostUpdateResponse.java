package SeoulMilk1_BE.post.dto.response.post;

import java.time.LocalDateTime;

public record PostUpdateResponse(Long postId, LocalDateTime updatedAt) {
    public static PostUpdateResponse from(Long postId, LocalDateTime updatedAt) {
        return new PostUpdateResponse(postId, updatedAt);
    }
}
