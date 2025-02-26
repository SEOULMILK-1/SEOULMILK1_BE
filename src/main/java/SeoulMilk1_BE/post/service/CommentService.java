package SeoulMilk1_BE.post.service;

import SeoulMilk1_BE.post.domain.Comment;
import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.post.dto.request.CommentCreateRequest;
import SeoulMilk1_BE.post.dto.request.CommentUpdateRequest;
import SeoulMilk1_BE.post.dto.response.CommentCreateResponse;
import SeoulMilk1_BE.post.dto.response.CommentDeleteResponse;
import SeoulMilk1_BE.post.dto.response.CommentReadResponse;
import SeoulMilk1_BE.post.dto.response.CommentUpdateResponse;
import SeoulMilk1_BE.post.repository.CommentRepository;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public CommentCreateResponse create(CommentCreateRequest request) {
        User user = userService.findOne(request.userId());
        Post post = postService.get(request.postId());
        Comment comment = Comment.builder()
                .user(user)
                .text(request.text())
                .build();
        comment.setPost(post);

        commentRepository.save(comment);

        return new CommentCreateResponse(comment.getId(), comment.getCreatedAt());
    }

    public CommentUpdateResponse update(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setText(request.text());

        return new CommentUpdateResponse(commentId, comment.getModifiedAt());
    }

    public CommentDeleteResponse delete(Long commentId) {
        commentRepository.deleteById(commentId);
        return new CommentDeleteResponse(commentId, LocalDateTime.now());
    }


    // 게시글에 등록된 댓글 존재 여부 체크
    public Boolean isExist(Long postId) {
        long l = commentRepository.countByPostId(postId);
        return l > 0;
    }
}
