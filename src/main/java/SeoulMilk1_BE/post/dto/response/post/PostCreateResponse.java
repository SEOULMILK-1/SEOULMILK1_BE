package SeoulMilk1_BE.post.dto.response.post;

import java.time.LocalDateTime;

public record PostCreateResponse(Long postId, LocalDateTime createdAt) {
}
