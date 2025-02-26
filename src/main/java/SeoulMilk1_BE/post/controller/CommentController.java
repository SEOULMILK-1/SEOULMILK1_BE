package SeoulMilk1_BE.post.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.post.dto.request.comment.CommentCreateRequest;
import SeoulMilk1_BE.post.dto.request.comment.CommentUpdateRequest;
import SeoulMilk1_BE.post.dto.response.comment.CommentCreateResponse;
import SeoulMilk1_BE.post.dto.response.comment.CommentDeleteResponse;
import SeoulMilk1_BE.post.dto.response.comment.CommentUpdateResponse;
import SeoulMilk1_BE.post.service.CommentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "댓글 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ApiResponse<CommentCreateResponse> create(@RequestBody CommentCreateRequest request) {
        return ApiResponse.onSuccess(commentService.create(request));
    }

    @PatchMapping("/{commentId}")
    public ApiResponse<CommentUpdateResponse> update(@PathVariable("commentId") Long commentId, @RequestBody CommentUpdateRequest request) {
        return ApiResponse.onSuccess(commentService.update(commentId, request));
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<CommentDeleteResponse> delete(@PathVariable("commentId") Long commentId) {
        return ApiResponse.onSuccess(commentService.delete(commentId));
    }
}
