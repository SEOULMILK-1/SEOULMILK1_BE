package SeoulMilk1_BE.post.service;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.global.service.S3Service;
import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.post.domain.type.Type;
import SeoulMilk1_BE.post.dto.request.PostCreateRequest;
import SeoulMilk1_BE.post.dto.response.PostCreateResponse;
import SeoulMilk1_BE.post.dto.response.PostDeleteResponse;
import SeoulMilk1_BE.post.dto.response.PostDetailResponse;
import SeoulMilk1_BE.post.dto.response.PostUpdateResponse;
import SeoulMilk1_BE.post.repository.PostRepository;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final S3Service s3Service;
    private final CommentService commentService;

    public PostCreateResponse save(PostCreateRequest request, List<MultipartFile> files) {
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

        return new PostCreateResponse(post.getPostId(), post.getCreatedAt());
    }

    public PostDetailResponse findOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        // 조회 수 증가.
        post.updateViews();

        return new PostDetailResponse(post.getPostId(), post.getUser().getName(), post.getTitle(), post.getContent(), post.getType(), post.getViews(), post.getPostImgUrl(), post.getCreatedAt(), post.getModifiedAt());
    }

    public PostUpdateResponse update(Long postId, PostCreateRequest request, List<MultipartFile> files) {
        List<String> postImgList = s3Service.uploadFiles(files);
        Post post = postRepository.findById(postId).orElseThrow();
        post.updatePost(request.title(), request.content(), Type.valueOf(request.type()), postImgList);

        return new PostUpdateResponse(post.getPostId(), post.getModifiedAt());
    }


    public PostDeleteResponse delete(Long postId) {
        // 댓글 존재하는지 검증.
        if (!commentService.isExist(postId)) {
            throw new GeneralException(ErrorStatus.COMMENT_IS_EXIST);
        }

        Post post = postRepository.findById(postId).orElseThrow();

        // 비활성일자 설정.
        post.setIsValid(false);
        post.setInactiveDate(LocalDateTime.now().plusDays(7));

        return new PostDeleteResponse(postId, post.getInactiveDate());
    }
}
