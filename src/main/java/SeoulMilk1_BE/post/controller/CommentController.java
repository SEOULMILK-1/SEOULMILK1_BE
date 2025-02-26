package SeoulMilk1_BE.post.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.post.dto.request.comment.CommentCreateRequest;
import SeoulMilk1_BE.post.dto.request.comment.CommentUpdateRequest;
import SeoulMilk1_BE.post.dto.response.comment.CommentCreateResponse;
import SeoulMilk1_BE.post.dto.response.comment.CommentDeleteResponse;
import SeoulMilk1_BE.post.dto.response.comment.CommentUpdateResponse;
import SeoulMilk1_BE.post.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "댓글 작성", description = "게시글 댓글 작성 ")
    @PostMapping
    public ApiResponse<CommentCreateResponse> createComment(@RequestBody CommentCreateRequest request) {
        return ApiResponse.onSuccess(commentService.create(request));
    }

    @Operation(summary = "댓글 수정", description = "게시글 댓글 수정")
    @PatchMapping("/{commentId}")
    public ApiResponse<CommentUpdateResponse> updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentUpdateRequest request) {
        return ApiResponse.onSuccess(commentService.update(commentId, request));
    }

    @Operation(summary = "댓글 삭제", description = "게시글 댓글 삭제")
    @DeleteMapping("/{commentId}")
    public ApiResponse<CommentDeleteResponse> deleteComment(@PathVariable("commentId") Long commentId) {
        return ApiResponse.onSuccess(commentService.delete(commentId));
    }
}
