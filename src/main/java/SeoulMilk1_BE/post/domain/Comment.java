package SeoulMilk1_BE.post.domain;

import SeoulMilk1_BE.global.domain.BaseTimeEntity;
import SeoulMilk1_BE.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "comments")
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Setter
    private String text;

    public void setPost(Post post) {
        this.post = post;
        post.getCommentList().add(this);
    }
}
