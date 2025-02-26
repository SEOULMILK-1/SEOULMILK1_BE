package SeoulMilk1_BE.post.domain;

import SeoulMilk1_BE.global.domain.BaseTimeEntity;
import SeoulMilk1_BE.post.domain.type.Type;
import SeoulMilk1_BE.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "views", nullable = false)
    private Long views;

    @Column(name = "is_valid", nullable = false)
    private Boolean isValid;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ElementCollection
    @CollectionTable(name = "post_img_list", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> postImgUrl = new ArrayList<>();

    @Builder
    public Post(User user, String title, String content, Long views, Boolean isValid, List postImgList) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.views = views;
        this.isValid = isValid;
        this.postImgUrl = postImgList;
    }
}
