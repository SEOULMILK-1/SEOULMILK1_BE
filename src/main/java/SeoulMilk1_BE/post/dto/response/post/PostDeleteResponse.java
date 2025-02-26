package SeoulMilk1_BE.post.dto.response.post;

import java.time.LocalDateTime;

public record PostDeleteResponse(Long postId, LocalDateTime inactiveDate) {
    public static PostDeleteResponse from(Long postId, LocalDateTime inactiveDate) {
        return new PostDeleteResponse(postId, inactiveDate);
    }
}
