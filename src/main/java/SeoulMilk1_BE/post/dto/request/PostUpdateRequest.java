package SeoulMilk1_BE.post.dto.request;

public record PostUpdateRequest(Long userId, String title, String content) {
}
