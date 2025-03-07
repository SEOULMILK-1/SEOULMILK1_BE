package SeoulMilk1_BE.post.repository.init;

import SeoulMilk1_BE.global.util.DummyDataInit;
import SeoulMilk1_BE.post.domain.Post;
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
@Order(3)
@DummyDataInit
public class PostInitializer implements ApplicationRunner {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (postRepository.count() > 0) {
            log.info("[Post] 더미 데이터 존재");
        } else {
            User user = userRepository.findById(1L)
                    .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

            List<Post> postList = new ArrayList<>();

            Post DUMMY_POST1 = Post.builder()
                    .user(user)
                    .title("서울우유협동조합 공지사항")
                    .content("조합원이 생산한 우유를 원료로 해서 공장에서 가공처리한 후 판매하는 사업으로 우유, 발효유, 유음료, 과ㆍ채음료, 치즈, 버터, 분유, 연유 등을 생산 판매하고 있습니다.")
                    .views(200L)
                    .isValid(true)
                    .build();

            Post DUMMY_POST2 = Post.builder()
                    .user(user)
                    .title("서울우유협동조합 신제품 출시 안내")
                    .content("최근 조합원이 생산한 신선한 우유를 사용하여 새로운 치즈 제품을 출시하였습니다. 다양한 요리에 활용할 수 있는 맛있는 치즈를 많은 사랑 부탁드립니다!")
                    .views(80L)
                    .isValid(true)
                    .build();

            Post DUMMY_POST3 = Post.builder()
                    .user(user)
                    .title("서울우유협동조합 연례 보고서 배포")
                    .content("안녕하세요, 서울우유협동조합입니다. 2024년 연례 보고서를 발행하였습니다. 조합원 여러분의 성원에 힘입어 지난 한 해 동안의 성과와 향후 계획을 담았습니다. 보고서는 협동조합 홈페이지에서 다운로드 가능합니다.")
                    .views(138L)
                    .isValid(true)
                    .build();

            postList.add(DUMMY_POST1);
            postList.add(DUMMY_POST2);
            postList.add(DUMMY_POST3);

            postRepository.saveAll(postList);
        }
    }
}
