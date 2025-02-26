package SeoulMilk1_BE.post.dto.request;

public record PostCreateRequest(Long userId, String title, String content, String type) {
}
