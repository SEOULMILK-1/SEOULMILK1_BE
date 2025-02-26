package SeoulMilk1_BE.post.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record PostDetailResponse(Long postId, String userName, String title, String content, Long views, List<String> imgUrls, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
