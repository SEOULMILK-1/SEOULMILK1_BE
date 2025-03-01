package SeoulMilk1_BE.post.repository.init;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.global.util.DummyDataInit;
import SeoulMilk1_BE.post.domain.Comment;
import SeoulMilk1_BE.post.domain.Post;
import SeoulMilk1_BE.post.repository.CommentRepository;
import SeoulMilk1_BE.post.repository.PostRepository;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.exception.UserNotFoundException;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.List;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Order(4)
@DummyDataInit
public class CommentInitializer implements ApplicationRunner {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (commentRepository.count() > 0) {
            log.info("[Post] 더미 데이터 존재");
        } else {
            User user = userRepository.findById(1L)
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

            Post post1 = postRepository.findById(1L).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));
            Post post2 = postRepository.findById(2L).orElseThrow(() -> new GeneralException(ErrorStatus.POST_NOT_FOUND));

            List<Comment> commentList = new ArrayList<>();

            Comment DUMMY_COMMENT1 = Comment.builder()
                    .user(user)
                    .post(post1)
                    .text("좋네요~^^")
                    .build();

            Comment DUMMY_COMMENT2 = Comment.builder()
                    .user(user)
                    .post(post2)
                    .text("굳굳~^^")
                    .build();

            Comment DUMMY_COMMENT3 = Comment.builder()
                    .user(user)
                    .post(post2)
                    .text("좋은 정보~^^")
                    .build();

            commentList.add(DUMMY_COMMENT1);
            commentList.add(DUMMY_COMMENT2);
            commentList.add(DUMMY_COMMENT3);

            commentRepository.saveAll(commentList);
        }
    }
}
