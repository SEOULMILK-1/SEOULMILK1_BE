package SeoulMilk1_BE.post.dto.request.post;

import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.post.domain.type.Type;
import SeoulMilk1_BE.user.domain.User;

import java.util.List;

public record PostCreateRequest(Long userId, String title, String content, String type) {
    public static Post of(User user, String title, String content, List<String> postImgList) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .isValid(true)
                .views(0L)
                .postImgList(postImgList)
                .build();
    }
}
