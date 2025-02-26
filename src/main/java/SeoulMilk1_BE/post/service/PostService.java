package SeoulMilk1_BE.post.service;

import SeoulMilk1_BE.global.service.S3Service;
import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.post.domain.type.Type;
import SeoulMilk1_BE.post.dto.request.PostUpdateRequest;
import SeoulMilk1_BE.post.dto.response.PostDetailResponse;
import SeoulMilk1_BE.post.dto.response.PostUpdateResponse;
import SeoulMilk1_BE.post.repository.PostRepository;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.repository.UserRepository;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final S3Service s3Service;

    @Transactional
    public PostUpdateResponse save(PostUpdateRequest request, List<MultipartFile> files) {
        List<String> postImgList = s3Service.uploadFiles(files);
        User user = userService.findOne(request.userId());

        Post post = Post.builder()
                .user(user)
                .title(request.title())
                .content(request.content())
                .isValid(true)
                .views(0L)
                .type(Type.valueOf(request.type()))
                .postImgList(postImgList)
                .build();

        postRepository.save(post);

        return new PostUpdateResponse(post.getPostId(), post.getCreatedAt());
    }

    @Transactional
    public PostDetailResponse findOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        // 조회 수 증가.
        post.updateViews();

        return new PostDetailResponse(post.getPostId(), post.getUser().getName(), post.getTitle(), post.getContent(), post.getType(), post.getViews(), post.getPostImgUrl(), post.getCreatedAt(), post.getModifiedAt());
    }
}
