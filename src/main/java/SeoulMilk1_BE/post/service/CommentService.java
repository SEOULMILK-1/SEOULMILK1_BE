package SeoulMilk1_BE.post.service;

import SeoulMilk1_BE.post.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Boolean isExist(Long postId) {
        long l = commentRepository.countByPostId(postId);
        return l > 0;
    }
}
