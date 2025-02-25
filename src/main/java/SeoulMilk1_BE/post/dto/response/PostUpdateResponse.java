package SeoulMilk1_BE.post.dto.response;

import java.time.LocalDateTime;

public record PostUpdateResponse(Long postId, LocalDateTime createdAt) {
}
