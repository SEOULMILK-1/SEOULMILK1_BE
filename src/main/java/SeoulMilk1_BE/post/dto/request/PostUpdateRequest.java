package SeoulMilk1_BE.post.dto.request;

import SeoulMilk1_BE.post.domain.type.Type;

public record PostUpdateRequest(Long userId, String title, String content, String type) {
}
