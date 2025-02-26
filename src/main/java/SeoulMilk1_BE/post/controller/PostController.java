package SeoulMilk1_BE.post.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.post.dto.request.PostUpdateRequest;
import SeoulMilk1_BE.post.dto.response.PostDetailResponse;
import SeoulMilk1_BE.post.dto.response.PostUpdateResponse;
import SeoulMilk1_BE.post.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Post", description = "게시판 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping
    public ApiResponse<PostUpdateResponse> create(@RequestPart PostUpdateRequest request, @RequestPart List<MultipartFile> files) {
        return ApiResponse.onSuccess(postService.save(request,files));
    }

    @GetMapping("{postId}")
    public ApiResponse<PostDetailResponse> readOne(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(postService.findOne(postId));
    }

}
