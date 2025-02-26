package SeoulMilk1_BE.post.dto.response;

import java.time.LocalDateTime;

public record PostDeleteResponse(Long postId, LocalDateTime inactiveDate) {
}
