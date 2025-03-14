package SeoulMilk1_BE.nts_tax.service;

import SeoulMilk1_BE.global.service.S3Service;
import SeoulMilk1_BE.nts_tax.dto.request.OcrApiRequest;
import SeoulMilk1_BE.nts_tax.dto.response.CustomOcrResponse;
import SeoulMilk1_BE.nts_tax.dto.response.IssueIdTaxResponse;
import SeoulMilk1_BE.nts_tax.util.OcrClient;
import SeoulMilk1_BE.nts_tax.dto.response.OcrApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static SeoulMilk1_BE.nts_tax.util.TaxConstants.DUPLICATE_TAX;

@Slf4j
@Service
@RequiredArgsConstructor
public class OcrService {

    @Value("${ocr.secretKey}")
    private String secret;

    @Value("${ocr.template-id}")
    private String templateIds;

    private final S3Service s3Service;
    private final OcrClient ocrClient;
    private final NtsTaxService ntsTaxService;

    public CustomOcrResponse callOcrApi(Long userId, MultipartFile multipartFile) {
        try {
            String imageUrl = s3Service.uploadFile(multipartFile);
            String ext = getFileExtension(multipartFile);

            OcrApiRequest request = OcrApiRequest.of(ext, imageUrl, templateIds, multipartFile);

            log.info("Sending request: {}", new ObjectMapper().writeValueAsString(request));
            OcrApiResponse response = ocrClient.callOcrApi(secret, request);
            log.info("OCR API Response: {}", response);

            IssueIdTaxResponse issueResponse = ntsTaxService.saveNtsTax(response, userId, imageUrl);

            if (issueResponse.isExist()) {
                return CustomOcrResponse.from(DUPLICATE_TAX.getMessage(), issueResponse.ntsTax());
            } else {
                return CustomOcrResponse.from(issueResponse.ntsTax());
            }
        } catch (JsonProcessingException e) {
            log.error("OCR API 호출 중 오류 발생", e);
            return CustomOcrResponse.from(e.getMessage());
        }
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }

        return "";
    }
}
