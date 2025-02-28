package SeoulMilk1_BE.nts_tax.dto.request;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Builder
public record OcrApiRequest(
        String version,
        String requestId,
        Long timestamp,
        String lang,
        List<Images> images
) {
    @Builder
    public record Images (
            String format,
            String name,
            String url,
            List<Integer> templateIds
    ) {}

    public static OcrApiRequest of(String ext, String imageUrl, String templateIds, MultipartFile file) {
        return OcrApiRequest.builder()
                .version("V2")
                .requestId(UUID.randomUUID().toString())
                .lang("ko")
                .timestamp(System.currentTimeMillis())
                .images(List.of(Images.builder()
                        .format(ext)
                        .name(file.getOriginalFilename())
                        .url(imageUrl)
                        .templateIds(Arrays.stream(templateIds.split(","))
                                .map(String::trim)
                                .map(Integer::parseInt)
                                .toList())
                        .build())
                ).build();
    }
}
