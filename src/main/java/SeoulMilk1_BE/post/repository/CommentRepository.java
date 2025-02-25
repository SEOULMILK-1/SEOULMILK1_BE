package SeoulMilk1_BE.post.repository;

import SeoulMilk1_BE.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
