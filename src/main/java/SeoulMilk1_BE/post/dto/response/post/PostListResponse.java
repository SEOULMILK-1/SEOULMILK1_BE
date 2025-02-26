package SeoulMilk1_BE.post.dto.response.post;

public record PostListResponse(Long postId, String title, String username) {
    public static PostListResponse from(Long postId, String title, String username) {
        return new PostListResponse(postId, title, username);
    }
}
