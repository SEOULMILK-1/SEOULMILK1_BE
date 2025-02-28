package SeoulMilk1_BE.nts_tax.controller;

import SeoulMilk1_BE.global.apiPayload.ApiResponse;
import SeoulMilk1_BE.nts_tax.service.OcrService;
import SeoulMilk1_BE.user.dto.response.OcrResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "Nts_Tax", description = "세금계산서 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tax")
public class NtsTaxController {

    private final OcrService ocrService;

    @Operation(summary = "OCR 텍스트 추출", description = "이미지 업로드 시 OCR 텍스트 추출 후 응답값으로 제공")
    @PostMapping(path = "/ocr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<OcrResponse> getOcrData(
            @AuthenticationPrincipal Long userId,
            @RequestParam MultipartFile file
    ) {
        return ApiResponse.onSuccess(ocrService.callApi(file));
    }
}
