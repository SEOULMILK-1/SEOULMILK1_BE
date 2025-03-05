package SeoulMilk1_BE.payment_resolution.domain;

import SeoulMilk1_BE.global.domain.BaseTimeEntity;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionReadResponse;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class PaymentResolution extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_resolution_id")
    private Long id;

    private String name;
    private String paymentRecipient;             // 지급 대상
    private String recipientBusinessNumber;      // 지급 대상 사업자 등록 번호
    private Long totalPaymentAmount;             // 정산 지급 총액
    private String paymentMethod;                // 지급 방법
    private String paymentAccount;               // 지급 계좌
    private String paymentPrincipal;             // 지급 주체
    private String principalBusinessNumber;      // 지급 주체 사업자 등록 번호
    private String approver;                     // 결재권자
    private LocalDateTime scheduledPaymentDate;
    private Long totalSupplyAmount;
    private Long totalAllAmount;

    @ElementCollection
    @CollectionTable(name = "payment_details",
            joinColumns = @JoinColumn(name = "payment_resolution_id"))
    private List<PaymentDetails> paymentDetailsList = new ArrayList<>();

    @Builder
    public PaymentResolution(String name, String paymentRecipient, String recipientBusinessNumber, Long totalPaymentAmount, String paymentMethod, String paymentAccount, String paymentPrincipal, String principalBusinessNumber, String approver, LocalDateTime scheduledPaymentDate, List<PaymentDetails> paymentDetailsList, Long totalSupplyAmount, Long totalAllAmount) {
        this.name = name;
        this.paymentRecipient = paymentRecipient;
        this.recipientBusinessNumber = recipientBusinessNumber;
        this.totalPaymentAmount = totalPaymentAmount;
        this.paymentMethod = paymentMethod;
        this.paymentAccount = paymentAccount;
        this.paymentPrincipal = paymentPrincipal;
        this.principalBusinessNumber = principalBusinessNumber;
        this.approver = approver;
        this.scheduledPaymentDate = scheduledPaymentDate;
        this.paymentDetailsList = paymentDetailsList;
        this.totalSupplyAmount = totalSupplyAmount;
        this.totalAllAmount = totalAllAmount;
    }

    public void updatePaymentResolution(PaymentResolutionReadResponse request) {
        this.paymentRecipient = request.paymentRecipient();
        this.recipientBusinessNumber = request.recipientBusinessNumber();
        this.totalPaymentAmount = request.totalPaymentAmount();
        this.paymentMethod = request.paymentMethod();
        this.paymentAccount = request.paymentAccount();
        this.paymentPrincipal = request.paymentPrincipal();
        this.principalBusinessNumber = request.principalBusinessNumber();
        this.approver = request.approver();
        this.scheduledPaymentDate = request.scheduledPaymentDate();
        this.paymentDetailsList = request.paymentDetails();
    }
}
