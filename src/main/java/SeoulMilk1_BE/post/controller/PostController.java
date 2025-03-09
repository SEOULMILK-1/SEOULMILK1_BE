package SeoulMilk1_BE.post.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.post.dto.request.post.PostCreateRequest;
import SeoulMilk1_BE.post.dto.request.post.PostListRequest;
import SeoulMilk1_BE.post.dto.response.post.*;
import SeoulMilk1_BE.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "게시글 작성", description = "게시글 작성")
    @PostMapping
    public ApiResponse<PostCreateResponse> createPost(@RequestPart PostCreateRequest request, @RequestPart List<MultipartFile> files) {
        return ApiResponse.onSuccess(postService.save(request, files));
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글 상세 조회")
    @GetMapping("/{postId}")
    public ApiResponse<PostDetailResponse> readPostOne(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(postService.findOne(postId));
    }

    @Operation(summary = "게시글 목록 조회", description = "게시글 목록 조회")
    @GetMapping
    public ApiResponse<List<PostListResponse>> readPostList(@RequestBody PostListRequest request) {
        return ApiResponse.onSuccess(postService.findList(request));
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정")
    @PatchMapping("/{postId}")
    public ApiResponse<PostUpdateResponse> updatePost(@PathVariable("postId") Long postId, @RequestPart PostCreateRequest request, @RequestPart List<MultipartFile> files) {
        return ApiResponse.onSuccess(postService.update(postId, request, files));
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ApiResponse<PostDeleteResponse> deletePost(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(postService.delete(postId));
    }

    @Operation(summary = "게시글 상단 고정 or 해제", description = "게시글 상단 고정")
    @PatchMapping("/pin/{postId}")
    public ApiResponse<String> pinPost(@PathVariable("postId") Long postId) {
        return ApiResponse.onSuccess(postService.pinPost(postId));
    }
}
