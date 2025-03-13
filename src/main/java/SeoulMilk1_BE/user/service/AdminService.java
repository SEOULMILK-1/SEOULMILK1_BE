package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.dto.response.AdminSearchTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.AdminSearchTaxResponseList;
import SeoulMilk1_BE.nts_tax.dto.response.AdminTaxDetailResponse;
import SeoulMilk1_BE.nts_tax.exception.NtsTaxNotFoundException;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.dto.response.PendingUserResponse;
import SeoulMilk1_BE.user.dto.response.PendingUserResponseList;
import SeoulMilk1_BE.user.dto.response.UserManageResponse;
import SeoulMilk1_BE.user.dto.response.UserManageResponseList;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.TAX_NOT_FOUND;
import static SeoulMilk1_BE.user.util.UserConstants.APPROVED;
import static SeoulMilk1_BE.user.util.UserConstants.DELETED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final NtsTaxRepository ntsTaxRepository;

    public PendingUserResponseList findPendingUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage = userRepository.findUsersByRoleAndIsAssigned(pageable);

        List<PendingUserResponse> responseList = userPage.stream()
                .map(PendingUserResponse::from)
                .toList();

        return PendingUserResponseList.of(userPage, responseList);
    }

    public UserManageResponseList findAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage = userRepository.findAllByIsDeleted(pageable);

        List<UserManageResponse> responseList = userPage.stream()
                .map(UserManageResponse::from)
                .toList();

        return UserManageResponseList.of(userPage, responseList);
    }

    @Transactional
    public String approvePendingUser(Long userId) {
        User user = userService.findUser(userId);
        user.assign();
        return APPROVED.getMessage();
    }

    @Transactional
    public String deleteUser(Long userId) {
        User user = userService.findUser(userId);
        user.delete();
        return DELETED.getMessage();
    }

    public AdminSearchTaxResponseList searchTax(int page, int size, String keyword, String startDate, String endDate, Long months, Boolean status) {
        String start = formatInputData(startDate);
        String end = formatInputData(endDate);

        Pageable pageable = PageRequest.of(page, size);
        Page<AdminSearchTaxResponse> hqTaxResponsePage = ntsTaxRepository.findTaxUsedInAdmin(pageable, keyword, start, end, months, status);

        Long totalElements = hqTaxResponsePage.getTotalElements();
        Integer totalPages = hqTaxResponsePage.getTotalPages();
        List<AdminSearchTaxResponse> responseList = hqTaxResponsePage.getContent();

        return AdminSearchTaxResponseList.of(totalElements, totalPages, responseList);
    }

    public AdminTaxDetailResponse getTaxDetail(Long ntsTaxId) {
        NtsTax ntsTax = ntsTaxRepository.findById(ntsTaxId)
                .orElseThrow(() -> new NtsTaxNotFoundException(TAX_NOT_FOUND));

        return AdminTaxDetailResponse.from(ntsTax);
    }

    private static String formatInputData(String inputData) {
        if (!StringUtils.hasText(inputData) || inputData.isEmpty()) {
            return "";
        }

        return inputData.replace("-", "")
                .replace("/", "")
                .replace(" ", "")
                .replace("\n", "")
                .replace(".", "");
    }
}