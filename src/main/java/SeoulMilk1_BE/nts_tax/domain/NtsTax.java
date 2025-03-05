package SeoulMilk1_BE.nts_tax.domain;

import SeoulMilk1_BE.global.domain.BaseTimeEntity;
import SeoulMilk1_BE.nts_tax.domain.type.*;
import SeoulMilk1_BE.nts_tax.dto.request.UpdateTaxRequest;
import SeoulMilk1_BE.user.domain.Team;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.nts_tax.dto.response.OcrApiResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static SeoulMilk1_BE.nts_tax.domain.type.Arap.AR;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class NtsTax extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nts_tax_id")
    private Long id;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Column(name = "issue_id", nullable = false, length = 24)
    private String issueId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "tax_image_url", nullable = false)
    private String taxImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private Arap arap;

    @Column(name = "issue_dt", length = 24)
    private String issueDt;

    @Column(name = "bukrs", columnDefinition = "varchar (4) default '1000'")
    private String bukrs;

    @Column(name = "bupla", columnDefinition = "varchar (4) default '1000'")
    private String bupla;

    @Column(name = "issue_date", nullable = false, length = 8)
    private String issueDate;

    @Column(name = "inter_no", length = 24)
    private String interNo;

    @Column(name = "asp_code", length = 8)
    private String aspCode;

    @Enumerated(EnumType.STRING)
    private TypeCode typeCode;

    @Enumerated(EnumType.STRING)
    private PurpCode purpCode;

    @Enumerated(EnumType.STRING)
    private AmendCode amendCode;

    @Column(name = "desc_text1", length = 150)
    private String desc_text1;

    @Column(name = "desc_text2", length = 150)
    private String desc_text2;

    @Column(name = "desc_text3", length = 150)
    private String desc_text3;

    // 공급자 관련

    @Column(name = "su_id", nullable = false, length = 13)
    private String suId;

    @Column(name = "su_min", length = 4)
    private String suMin;

    @Column(name = "su_name", length = 70)
    private String suName;

    @Column(name = "su_repres", length = 30)
    private String suRepres;

    @Column(name = "su_addr", length = 150)
    private String suAddr;

    @Column(name = "su_bustype", length = 40)
    private String suBusType;

    @Column(name = "su_indtype", length = 40)
    private String suIndType;

    @Column(name = "su_deptname", length = 40)
    private String suDeptName;

    @Column(name = "su_persname", length = 30)
    private String suPersName;

    @Column(name = "su_telno", length = 20)
    private String suTelNo;

    @Column(name = "su_hpno", length = 20)
    private String suHpNo;

    @Column(name = "su_email", length = 40)
    private String suEmail;

    // 공급 받는 자 관련

    @Enumerated(EnumType.STRING)
    private IpTypeCode ipTypeCode;

    @Column(name = "ip_id", nullable = false, length = 13)
    private String ipId;

    @Column(name = "ip_min", length = 4)
    private String ipMin;

    @Column(name = "ip_name", length = 70)
    private String ipName;

    @Column(name = "ip_repres", length = 30)
    private String ipRepres;

    @Column(name = "ip_addr", length = 150)
    private String ipAddr;

    @Column(name = "ip_bustype", length = 40)
    private String ipBusType;

    @Column(name = "ip_indtype", length = 40)
    private String ipIndType;

    @Column(name = "ip_deptname1", length = 40)
    private String ipDeptName1;

    @Column(name = "ip_persname1", length = 30)
    private String ipPersName1;

    @Column(name = "ip_telno1", length = 20)
    private String ipTelNo1;

    @Column(name = "ip_hpno1", length = 20)
    private String ipHpNo1;

    @Column(name = "ip_email1", length = 40)
    private String ipEmail1;

    @Column(name = "ip_deptname2", length = 40)
    private String ipDeptName2;

    @Column(name = "ip_persname2", length = 30)
    private String ipPersName2;

    @Column(name = "ip_telno2", length = 20)
    private String ipTelNo2;

    @Column(name = "ip_hpno2", length = 20)
    private String ipHpNo2;

    @Column(name = "ip_email2", length = 40)
    private String ipEmail2;

    // 금액 관련

    @Column(name = "chargetotal", nullable = false)
    private Long chargeTotal;

    @Column(name = "taxtotal", nullable = false)
    private Long taxTotal;

    @Column(name = "grandtotal", nullable = false)
    private Long grandTotal;

    @Column(name = "erdat", length = 8)
    private String erDat;

    @Column(name = "erzet", length = 6)
    private String erZet;

    // 생성자
    @Column(name = "ernam", length = 12)
    private String erNam;

    // 국세청 전송 일자
    @Column(name = "trans_date", length = 12)
    private String transDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "valid_status", nullable = false)
    private ValidStatus validStatus;

    @Column(name = "is_payment_written", nullable = false)
    private Boolean isPaymentWritten;

    public static NtsTax toNtsTax(OcrApiResponse response, User user, String imageUrl) {
        return NtsTax.builder()
                .team(user.getTeam()) // 소속 추가
                .issueId(formatInputData(getInferText(response, "승인번호")))
                .arap(AR)   // 임시 지정
                .suId(formatInputData(getInferText(response, "공급자 등록번호")))
                .suDeptName(formatInputData(getInferText(response, "공급자 상호 법인명")))
                .suPersName(formatName(getInferText(response, "공급자 성명")))
                .suAddr(getInferText(response, "공급자 사업장 주소"))
                .suEmail(getInferText(response, "공급자 이메일"))
                .ipId(formatInputData(getInferText(response, "공급받는자 등록번호")))
                .ipName(getInferText(response, "공급받는자 상호 법인명"))
                .ipPersName1(formatName(getInferText(response, "공급받는자 성명")))
                .ipAddr(getInferText(response, "공급받는자 사업장 주소"))
                .issueDate(formatInputData(getInferText(response, "작성일자")))
                .chargeTotal(stringToLong(getInferText(response, "공급가액")))
                .taxTotal(stringToLong(getInferText(response, "세액")))
                .grandTotal(stringToLong(getInferText(response, "공급가액")) + stringToLong(getInferText(response, "세액")))
                .erDat(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .erZet(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")))
                .user(user)
                .taxImgUrl(imageUrl)
                .validStatus(ValidStatus.WAIT)
                .isPaymentWritten(false)
                .build();
    }

    public void updateNtsTax(UpdateTaxRequest request) {
        this.issueId = formatInputData(request.issueId());
        this.suId = formatInputData(request.suId());
        this.ipId = formatInputData(request.ipId());
        this.issueDate = formatInputData(request.issueDate());
        this.chargeTotal = stringToLong(request.chargeTotal());
    }

    private static String getInferText(OcrApiResponse response, String fieldName) {
        return response.images().stream()
                .flatMap(image -> image.fields().stream())
                .filter(field -> field.name().equals(fieldName))
                .map(OcrApiResponse.ImageResult.Field::inferText)
                .findFirst()
                .orElse("");
    }

    private static Long stringToLong(String inputText) {
        if (!StringUtils.hasText(inputText) || inputText.isEmpty()) {
            return 0L;
        }

        return Long.parseLong(inputText.replace(",", "").replace(".", ""));
    }

    private static String formatInputData(String inputData) {
        if (!StringUtils.hasText(inputData) || inputData.isEmpty()) {
            return "";
        }

        return inputData.replace("-", "")
                .replace("/", "")
                .replace(" ", "")
                .replace("\n", "");
    }


    private static String formatName(String name) {
        if (!StringUtils.hasText(name) || name.isEmpty()) {
            return "";
        }

        return name.split(" ")[0];
    }

    public void updateStatus(int status) {
        switch (status) {
            case 0:
                this.validStatus = ValidStatus.APPROVE;
                break;
            case 1:
                this.validStatus = ValidStatus.REFUSED;
                break;
        }
    }
}
