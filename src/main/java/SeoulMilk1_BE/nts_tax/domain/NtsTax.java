package SeoulMilk1_BE.nts_tax.domain;

import SeoulMilk1_BE.nts_tax.domain.type.*;
import SeoulMilk1_BE.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class NtsTax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(nullable = false, length = 255)
    private String taxImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 2)
    private Arap arap;

    @Column(length = 24)
    private String issueDt;

    @Column(columnDefinition = "varchar (4) default '1000'")
    private String bukrs;

    @Column(columnDefinition = "varchar (4) default '1000'")
    private String bupla;

    @Column(nullable = false, length = 8)
    private String issueDate;

    @Column(length = 24)
    private String insertNo;

    @Column(length = 8)
    private String aspCode;

    @Enumerated(EnumType.STRING)
    private TypeCode typeCode;

    @Enumerated(EnumType.STRING)
    private PurpCode purpCode;

    @Enumerated(EnumType.STRING)
    private AmendCode amendCode;

    @Column(length = 150)
    private String desc_text1;

    @Column(length = 150)
    private String desc_text2;

    @Column(length = 150)
    private String desc_text3;

    // 공급자 관련

    @Column(nullable = false, length = 13)
    private String suId;

    @Column(length = 4)
    private String suMin;

    @Column(length = 70)
    private String suName;

    @Column(length = 30)
    private String suRepres;

    @Column(length = 150)
    private String suAddr;

    @Column(length = 40)
    private String busType;

    @Column(length = 40)
    private String suDeptName;

    @Column(length = 30)
    private String suPersName;

    @Column(length = 20)
    private String suTelNo;

    @Column(length = 20)
    private String suHpNo;

    @Column(length = 40)
    private String email;

    // 공급 받는 자 관련

    @Enumerated(EnumType.STRING)
    private IpTypeCode ipTypeCode;

    @Column(nullable = false, length = 13)
    private String ipId;

    @Column(length = 4)
    private String ipMin;

    @Column(length = 70)
    private String ipName;

    @Column(length = 30)
    private String ipRepres;

    @Column(length = 150)
    private String ipAddr;

    @Column(length = 40)
    private String ipBusType;

    @Column(length = 40)
    private String ipIndType;

    @Column(length = 40)
    private String ipDeptName1;

    @Column(length = 30)
    private String ipPersName1;

    @Column(length = 20)
    private String ipTelNo1;

    @Column(length = 20)
    private String hpNo1;

    // 금액 관련

    @Column(nullable = false)
    private Integer chargeTotal;

    @Column(nullable = false)
    private Integer taxTotal;

    @Column(nullable = false)
    private Integer grandTotal;

    // 생성자
    @Column(length = 12)
    private String ernam;

    // 국세청 전송 일자
    @Column(length = 12)
    private String transDate;
}
