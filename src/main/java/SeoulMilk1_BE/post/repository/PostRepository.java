package SeoulMilk1_BE.post.repository;

import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.post.dto.response.post.PostListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
