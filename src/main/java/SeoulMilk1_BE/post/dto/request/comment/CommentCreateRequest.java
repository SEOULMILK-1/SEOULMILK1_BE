package SeoulMilk1_BE.post.dto.request.comment;

import SeoulMilk1_BE.post.domain.Comment;
import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.user.domain.User;

public record CommentCreateRequest(Long postId, Long userId, String text) {
    public static Comment of(User user, Post post, String text) {
        Comment comment = Comment.builder()
                .user(user)
                .text(text)
                .build();
        comment.setPost(post);

        return comment;
    }
}
