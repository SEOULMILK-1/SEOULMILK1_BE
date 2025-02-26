package SeoulMilk1_BE.post.repository;

import SeoulMilk1_BE.post.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT COUNT(*) FROM Comments WHERE POST_ID=:postId", nativeQuery = true)
    long countByPostId(Long postId);
}
