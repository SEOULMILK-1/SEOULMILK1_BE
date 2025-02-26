package SeoulMilk1_BE.post.dto.response.comment;

public record CommentReadResponse(Long commentId, String username, String text) {
    public static CommentReadResponse from(Long commentId, String username, String text) {
        return new CommentReadResponse(commentId, username, text);
    }
}
