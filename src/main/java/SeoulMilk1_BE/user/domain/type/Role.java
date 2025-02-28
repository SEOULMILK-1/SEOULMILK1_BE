package SeoulMilk1_BE.user.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    HQ_USER("ROLE_HQ_USER"),
    CUSTOMER_USER("ROLE_CUSTOMER_USER"),
    ;

    private final String authority;

    public Collection<? extends GrantedAuthority> getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.authority));
    }
}
