package SeoulMilk1_BE.user.domain;

import SeoulMilk1_BE.user.dto.request.UpdateUserRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "business_number")
    private String businessNumber;

    @Column(name = "bank")
    private String bank;

    @Column(name = "account")
    private String account;

    @Builder
    public Team(String name, String address, String phone, String email, String businessNumber, String bank, String account) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.businessNumber = businessNumber;
        this.bank = bank;
        this.account = account;
    }

    public void updateTeam(UpdateUserRequest request) {
        this.bank = request.bank();
        this.account = request.account();
    }
}
