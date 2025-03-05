package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record UserManageResponseList(
        Long totalElements,
        Integer totalPages,
        List<UserManageResponse> responseList
) {
    public static UserManageResponseList of(Page<User> page, List<UserManageResponse> responseList) {
        return UserManageResponseList.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .responseList(responseList)
                .build();
    }
}