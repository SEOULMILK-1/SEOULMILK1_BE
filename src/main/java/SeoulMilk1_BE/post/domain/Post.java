package SeoulMilk1_BE.post.domain;

import SeoulMilk1_BE.global.domain.BaseTimeEntity;
import SeoulMilk1_BE.post.domain.type.Type;
import SeoulMilk1_BE.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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
    @Setter
    private Boolean isValid;

    @Enumerated(EnumType.STRING)
    private Type type;

    @ElementCollection
    @CollectionTable(name = "post_img_list", joinColumns = @JoinColumn(name = "post_id"))
    private List<String> postImgUrl = new ArrayList<>();

    @Setter
    private LocalDateTime inactiveDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @Builder
    public Post(User user, String title, String content, Long views, Boolean isValid, Type type, List postImgList) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.views = views;
        this.isValid = isValid;
        this.type = type;
        this.postImgUrl = postImgList;
    }

    public void updatePost(String title, String content, Type type, List<String> postImgUrl) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.postImgUrl = postImgUrl;
    }

    public void updateViews() {
        this.views += 1;
    }
}
