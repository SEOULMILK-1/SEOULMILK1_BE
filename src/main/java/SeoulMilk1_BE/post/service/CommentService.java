package SeoulMilk1_BE.post.service;

import SeoulMilk1_BE.post.domain.Comment;
import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.post.dto.request.comment.CommentCreateRequest;
import SeoulMilk1_BE.post.dto.request.comment.CommentUpdateRequest;
import SeoulMilk1_BE.post.dto.response.comment.CommentCreateResponse;
import SeoulMilk1_BE.post.dto.response.comment.CommentDeleteResponse;
import SeoulMilk1_BE.post.dto.response.comment.CommentUpdateResponse;
import SeoulMilk1_BE.post.repository.CommentRepository;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentCreateResponse create(CommentCreateRequest request) {
        User user = userService.findUser(request.userId());
        Post post = postService.get(request.postId());
        Comment comment = CommentCreateRequest.of(user, post, request.text());

        commentRepository.save(comment);

        return CommentCreateResponse.from(comment.getId(), comment.getCreatedAt());
    }

    public CommentUpdateResponse update(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.updateText(request.text());

        return CommentUpdateResponse.from(commentId, comment.getModifiedAt());
    }

    public CommentDeleteResponse delete(Long commentId) {
        commentRepository.deleteById(commentId);
        return CommentDeleteResponse.from(commentId, LocalDateTime.now());
    }

}
