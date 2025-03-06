package SeoulMilk1_BE.post.service;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.global.service.S3Service;
import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.post.domain.type.Type;
import SeoulMilk1_BE.post.dto.request.post.PostCreateRequest;
import SeoulMilk1_BE.post.dto.request.post.PostListRequest;
import SeoulMilk1_BE.post.dto.response.comment.CommentReadResponse;
import SeoulMilk1_BE.post.dto.response.post.*;
import SeoulMilk1_BE.post.repository.PostRepository;
import SeoulMilk1_BE.post.util.PostConstants;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final S3Service s3Service;

    public PostCreateResponse save(PostCreateRequest request, List<MultipartFile> files) {
        List<String> postImgList = s3Service.uploadFiles(files);
        User user = userService.findUser(request.userId());

        Post post = PostCreateRequest.of(user, request.title(), request.content(), Type.valueOf(request.type()), postImgList);

        postRepository.save(post);

        return PostCreateResponse.from(post.getId());
    }

    public PostDetailResponse findOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        List<CommentReadResponse> comments = post.getCommentList().stream().map(c -> new CommentReadResponse(c.getId(), c.getUser().getName(), c.getText())).collect(Collectors.toList());
        // 조회 수 증가.
        post.updateViews();

        return PostDetailResponse.from(post.getId(), post.getUser().getName(), post.getTitle(), post.getContent(), post.getType(), post.getViews(), post.getPostImgUrl(), post.getCreatedAt(), post.getModifiedAt(), comments);
    }

    public List<PostListResponse> findList(PostListRequest request) {
        Pageable pageable = PageRequest.of(request.page(), request.size());
        List<Post> result = postRepository.findAllByOrderByModifiedAtDesc(pageable).getContent();
        return result.stream().map(r -> PostListResponse.from(r.getId(), r.getTitle(), r.getUser().getName())).collect(Collectors.toList());
    }

    public PostUpdateResponse update(Long postId, PostCreateRequest request, List<MultipartFile> files) {
        List<String> postImgList = s3Service.uploadFiles(files);
        Post post = postRepository.findById(postId).orElseThrow();
        post.updatePost(request.title(), request.content(), Type.valueOf(request.type()), postImgList);

        return PostUpdateResponse.from(post.getId(), post.getModifiedAt());
    }


    public PostDeleteResponse delete(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();

        // 댓글 존재하는지 검증.
        if (post.getCommentList().size() > 0) {
            throw new GeneralException(ErrorStatus.COMMENT_IS_EXIST);
        }

        // 게시글 비활성화
        post.deactivate();

        return PostDeleteResponse.from(postId, post.getInactiveDate());
    }

    // 엔티티 조회용
    public Post get(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
    }

    // 게시글 상단 고정
    @Transactional
    public String pinPost(Long postId) {
        int count = postRepository.countByPinTrue();

        if (count >= 4) {
            return PostConstants.PIN_FAILED.getMessage();
        }
        else {
            Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
            if (post.getPin() == true)
                throw new GeneralException(ErrorStatus.ALREADY_PINED);
            post.doPin();
            return PostConstants.PIN_SUCCESS.getMessage();
        }
    }

    @Transactional
    public String unPinPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
        if (post.getPin() == false)
            throw new GeneralException(ErrorStatus.ALREADY_UN_PINED);
        post.unPin();
        return PostConstants.UN_PIN_SUCCESS.getMessage();
    }
}
