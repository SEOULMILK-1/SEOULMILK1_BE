package SeoulMilk1_BE.user.dto.request;

import java.util.List;

public record HqAddManageCsRequest(
        List<String> csNames
) {
}