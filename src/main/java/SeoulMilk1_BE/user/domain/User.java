package SeoulMilk1_BE.user.domain;

import SeoulMilk1_BE.global.domain.BaseTimeEntity;
import SeoulMilk1_BE.user.domain.type.Role;
import SeoulMilk1_BE.user.dto.request.UpdateUserRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @JoinColumn(name = "team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "is_assigned", nullable = false)
    private Boolean isAssigned;

    @Column(name = "phone")
    private String phone;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "account")
    private String account;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Builder
    public User(String loginId, String password, String name, String email, Boolean isAssigned,
                String phone, String profileImageUrl, String account, Role role, Team team) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.isAssigned = isAssigned;
        this.phone = phone;
        this.profileImageUrl = profileImageUrl;
        this.account = account;
        this.role = role;
        this.team = team;
    }

    public void assign() {
        this.isAssigned = true;
    }

    public void updateUser(UpdateUserRequest request) {
        this.loginId = request.loginId();
        this.email = request.email();
        this.phone = formatPhone(request.phone());
        this.account = request.account();
    }

    private static String formatPhone(String phone) {
        return phone.replace("-", "");
    }
}