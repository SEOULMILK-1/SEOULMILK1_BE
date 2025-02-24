package SeoulMilk1_BE.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Post", description = "게시판 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
}
