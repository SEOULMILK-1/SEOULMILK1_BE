package SeoulMilk1_BE.user.service;

import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.dto.response.*;
import SeoulMilk1_BE.nts_tax.exception.NtsTaxNotFoundException;
import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import SeoulMilk1_BE.user.domain.Team;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.dto.request.HqAddManageCsRequest;
import SeoulMilk1_BE.user.dto.response.*;
import SeoulMilk1_BE.user.exception.TeamNotFoundException;
import SeoulMilk1_BE.user.exception.UserNotFoundException;
import SeoulMilk1_BE.user.repository.TeamRepository;
import SeoulMilk1_BE.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus.*;
import static SeoulMilk1_BE.user.util.UserConstants.ADD_MANAGE_CS_SUCCESS;
import static SeoulMilk1_BE.user.util.UserConstants.DELETE_MANAGE_CS_SUCCESS;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HqService {

    private final NtsTaxRepository ntsTaxRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public HqTaxResponseList getTaxInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        List<Team> teamList = user.getManageTeams().stream()
                .map(teamId -> teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND)))
                .toList();

        List<HqTaxResponse> responseList = ntsTaxRepository.findAllByIsPaymentWrittenAndManageCs(teamList).stream()
                .map(HqTaxResponse::from)
                .toList();

        return HqTaxResponseList.from(responseList);
    }

    public HqSearchTaxResponseList searchTax(int page, int size, String keyword, String startDate, String endDate, Long months, Boolean status, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        List<Team> teamList = user.getManageTeams().stream()
                .map(teamId -> teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND)))
                .toList();

        String start = formatInputData(startDate);
        String end = formatInputData(endDate);

        Pageable pageable = PageRequest.of(page, size);
        Page<HqSearchTaxResponse> hqTaxResponsePage = ntsTaxRepository.findTaxUsedInHQ(pageable, keyword, start, end, months, status, teamList);

        Long totalElements = hqTaxResponsePage.getTotalElements();
        Integer totalPages = hqTaxResponsePage.getTotalPages();
        List<HqSearchTaxResponse> responseList = hqTaxResponsePage.getContent();

        return HqSearchTaxResponseList.of(totalElements, totalPages, responseList);
    }

    public HqTaxDetailResponse getTaxDetail(Long ntsTaxId) {
        NtsTax ntsTax = ntsTaxRepository.findById(ntsTaxId)
                .orElseThrow(() -> new NtsTaxNotFoundException(TAX_NOT_FOUND));

        return HqTaxDetailResponse.from(ntsTax);
    }

    public HqSearchCsNameResponseList searchCsName(String keyword) {
        List<HqSearchCsNameResponse> responseList = teamRepository.findByNameContaining(keyword).stream()
                .map(HqSearchCsNameResponse::from)
                .toList();

        return HqSearchCsNameResponseList.from(responseList);
    }

    public HqSearchCsResponseList searchCs(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size);
        List<Team> teamList;

        if (StringUtils.isEmpty(keyword)) {
            teamList = teamRepository.findAll();
        } else {
            teamList = teamRepository.findByNameContaining(keyword);
        }

        Page<HqSearchCsResponse> responsePage = userRepository.findByTeamInAndIsDeleted(teamList, pageable)
                .map(HqSearchCsResponse::from);

        return HqSearchCsResponseList.of(responsePage.getTotalElements(), responsePage.getTotalPages(), responsePage.getContent());
    }

    public HqManageCsResponseList getManageCsList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        List<Long> teamIds = user.getManageTeams();

        List<HqManageCsResponse> responseList = new ArrayList<>();

        for (Long teamId : teamIds) {
            Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND));
            responseList.add(HqManageCsResponse.from(team));
        }

        return HqManageCsResponseList.from(responseList);
    }

    @Transactional
    public String addManageCs(Long userId, HqAddManageCsRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        for (Long teamId : request.teamIds()) {
            Team team = teamRepository.findById(teamId).orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND));
            user.addManageTeam(team);
        }

        return ADD_MANAGE_CS_SUCCESS.getMessage();
    }

    @Transactional
    public String deleteManageCs(Long userId, Long teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException(TEAM_NOT_FOUND));

        user.removeManageTeam(team);

        return DELETE_MANAGE_CS_SUCCESS.getMessage();
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
