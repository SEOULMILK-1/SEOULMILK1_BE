package SeoulMilk1_BE.post.dto.request;

public record CommentCreateRequest(Long postId, Long userId, String text) {
}
