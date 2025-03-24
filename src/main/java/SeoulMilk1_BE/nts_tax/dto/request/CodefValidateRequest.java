package SeoulMilk1_BE.nts_tax.dto.request;

import SeoulMilk1_BE.auth.util.RSAUtil;
import SeoulMilk1_BE.nts_tax.util.CodefConfigProperties;
import lombok.Builder;

@Builder
public record CodefValidateRequest(
        String organization,
        String loginType,
        String certType,
        String certFile,
        String certPassword,
        String supplierRegNumber,
        String contractorRegNumber,
        String approvalNo,
        String reportingDate,
        String supplyValue
) {
    public static CodefValidateRequest from(CodefConfigProperties properties, CodefApiRequest request) {
        String password;
        try {
            password = RSAUtil.encryptRSA(properties.getClient_password(), properties.getClient_publickey());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return CodefValidateRequest.builder()
                .organization("0004")
                .loginType("0")
                .certType("pfx")
                .certFile(properties.getClient_pfx())
                .certPassword(password)
                .supplierRegNumber(request.suId())
                .contractorRegNumber(request.ipId())
                .approvalNo(request.issueId())
                .reportingDate(request.issueDate())
                .supplyValue(request.chargeTotal())
                .build();
    }
}
