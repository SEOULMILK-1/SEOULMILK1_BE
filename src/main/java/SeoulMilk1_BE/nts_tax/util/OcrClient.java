package SeoulMilk1_BE.nts_tax.util;

import SeoulMilk1_BE.nts_tax.dto.request.OcrRequest;
import SeoulMilk1_BE.user.dto.response.OcrResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ocrClient", url = "${ocr.api-url}")
public interface OcrClient {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    OcrResponse callOcrApi(
            @RequestHeader("X-OCR-SECRET") String secretKey,
            @RequestBody OcrRequest ocrRequest
    );
}