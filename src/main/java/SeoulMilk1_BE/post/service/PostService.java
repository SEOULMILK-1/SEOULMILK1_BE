package SeoulMilk1_BE.post.service;

import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.post.dto.request.PostUpdateRequest;
import SeoulMilk1_BE.post.dto.response.PostUpdateResponse;
import SeoulMilk1_BE.post.repository.PostRepository;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.repository.UserRepository;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;

    @Transactional
    public Long save(PostUpdateRequest request) {
        User user = userService.findOne(request.userId());
        Post post = Post.builder()
                .user(user)
                .title(request.title())
                .content(request.content())
                .isValid(true)
                .views(0L)
                .build();

        postRepository.save(post);

        return post.getPostId();
    }
}
