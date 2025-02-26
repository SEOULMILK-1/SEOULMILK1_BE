package SeoulMilk1_BE.post.dto.response;

import java.time.LocalDateTime;

public record PostCreateResponse(Long postId, LocalDateTime createdAt) {
}
