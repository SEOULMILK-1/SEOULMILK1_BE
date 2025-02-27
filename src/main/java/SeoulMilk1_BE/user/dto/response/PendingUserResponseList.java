package SeoulMilk1_BE.user.dto.response;

import SeoulMilk1_BE.user.domain.User;
import lombok.Builder;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
public record PendingUserResponseList(
        Long totalElements,
        Integer totalPages,
        List<PendingUserResponse> responseList
) {
    public static PendingUserResponseList of(Page<User> page, List<PendingUserResponse> responseList) {
        return PendingUserResponseList.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .responseList(responseList)
                .build();
    }
}