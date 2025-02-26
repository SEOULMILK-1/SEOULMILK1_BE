package SeoulMilk1_BE.post.dto.request.comment;

public record CommentCreateRequest(Long postId, Long userId, String text) {
}
