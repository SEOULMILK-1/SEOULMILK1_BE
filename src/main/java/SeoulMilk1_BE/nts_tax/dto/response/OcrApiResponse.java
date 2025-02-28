package SeoulMilk1_BE.nts_tax.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record OcrApiResponse(
        String version,
        String requestId,
        long timestamp,
        List<ImageResult> images
) {
    public record ImageResult(
            String uid,
            String name,
            String inferResult,
            String message,
            MatchedTemplate matchedTemplate,
            ValidationResult validationResult,
            List<Field> fields,
            Title title
    ) {
        public record MatchedTemplate(
                int id,
                String name
        ) {
        }

        public record ValidationResult(
                String result
        ) {
        }

        public record Field(
                String name,
                String valueType,
                String inferText,
                double inferConfidence
        ) {
        }

        public record Title(
                String name,
                String inferText,
                double inferConfidence
        ) {
        }
    }
}
