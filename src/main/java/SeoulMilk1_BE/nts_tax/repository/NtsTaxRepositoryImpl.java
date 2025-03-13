package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.domain.type.ValidStatus;
import SeoulMilk1_BE.nts_tax.dto.response.AdminSearchTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqSearchTaxResponse;
import SeoulMilk1_BE.user.domain.Team;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

import static SeoulMilk1_BE.nts_tax.domain.QNtsTax.ntsTax;
import static SeoulMilk1_BE.nts_tax.domain.type.ValidStatus.APPROVE;

@Repository
@RequiredArgsConstructor
public class NtsTaxRepositoryImpl implements NtsTaxRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<AdminSearchTaxResponse> findTaxUsedInAdmin(Pageable pageable, String keyword, String startDate, String endDate, Long months, Boolean status) {
        List<AdminSearchTaxResponse> results = queryFactory.select(
                        Projections.constructor(AdminSearchTaxResponse.class,
                                ntsTax.id,
                                ntsTax.title,
                                Expressions.stringTemplate("CONCAT(SUBSTRING(issueDate, 1, 4), '.', SUBSTRING(issueDate, 5, 2), '.', SUBSTRING(issueDate, 7, 2))").as("formattedIssueDate"),
                                ntsTax.team.name,
                                Expressions.cases()
                                        .when(ntsTax.isPaymentWritten.isTrue()).then("반영")
                                        .otherwise("미반영").as("status")
                        )
                )
                .from(ntsTax)
                .where(containsKeyword(keyword),
                        betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenHqStatus(status),
                        ntsTax.validStatus.eq(APPROVE))
                .offset((pageable.getOffset()))
                .orderBy(ntsTax.issueDate.desc())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(ntsTax.id)
                .from(ntsTax)
                .where(containsKeyword(keyword),
                        betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenHqStatus(status),
                        ntsTax.validStatus.eq(APPROVE));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<HqSearchTaxResponse> findTaxUsedInHQ(Pageable pageable, String keyword, String startDate, String endDate, Long months, Boolean status, List<Team> teamList) {
        List<HqSearchTaxResponse> results = queryFactory.select(
                        Projections.constructor(HqSearchTaxResponse.class,
                                ntsTax.id,
                                ntsTax.title,
                                Expressions.stringTemplate("CONCAT(SUBSTRING(issueDate, 1, 4), '.', SUBSTRING(issueDate, 5, 2), '.', SUBSTRING(issueDate, 7, 2))").as("formattedIssueDate"),
                                ntsTax.team.name,
                                Expressions.cases()
                                        .when(ntsTax.isPaymentWritten.isTrue()).then("반영")
                                        .otherwise("미반영").as("status")
                        )
                )
                .from(ntsTax)
                .where(containsKeyword(keyword),
                        betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenHqStatus(status),
                        ntsTax.team.in(teamList),
                        ntsTax.validStatus.eq(APPROVE))
                .offset((pageable.getOffset()))
                .orderBy(ntsTax.issueDate.desc())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(ntsTax.id)
                .from(ntsTax)
                .where(containsKeyword(keyword),
                        betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenHqStatus(status),
                        ntsTax.team.in(teamList),
                        ntsTax.validStatus.eq(APPROVE));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<CsSearchTaxResponse> findTaxUsedInCS(Pageable pageable, Long userId, String startDate, String endDate, Long months, ValidStatus status) {
        List<CsSearchTaxResponse> results = queryFactory.select(
                        Projections.constructor(CsSearchTaxResponse.class,
                                ntsTax.id,
                                ntsTax.title,
                                Expressions.stringTemplate("CONCAT(SUBSTRING(issueDate, 1, 4), '.', SUBSTRING(issueDate, 5, 2), '.', SUBSTRING(issueDate, 7, 2))").as("formattedIssueDate"),
                                ntsTax.team.name,
                                ntsTax.validStatus
                        )
                )
                .from(ntsTax)
                .where(equalUser(userId),
                        betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenCsStatus(status))
                .offset((pageable.getOffset()))
                .orderBy(ntsTax.issueDate.desc())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(ntsTax.id)
                .from(ntsTax)
                .where(equalUser(userId),
                        betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenCsStatus(status));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }


    private BooleanExpression containsKeyword(String keyword) {
        return StringUtils.hasText(keyword) ? ntsTax.team.name.containsIgnoreCase(keyword) : null;
    }

    private BooleanExpression equalUser(Long userId) {
        return ntsTax.user.id.eq(userId);
    }

    private BooleanExpression betweenDate(String startDate, String endDate) {
        String start = StringUtils.hasText(startDate) ? startDate : null;
        String end = StringUtils.hasText(endDate) ? endDate : null;

        if (start == null && end == null) {
            return null;
        } else if (start == null) {
            return ntsTax.issueDate.loe(end);
        } else if (end == null) {
            return ntsTax.issueDate.goe(start);
        }

        return ntsTax.issueDate.between(start, end);
    }

    private BooleanExpression betweenMonths(Long months) {
        if (months != null) {
            String targetDate = String.valueOf(LocalDate.now().minusMonths(months));
            return ntsTax.issueDate.goe(targetDate);
        }

        return null;
    }

    private BooleanExpression betweenHqStatus(Boolean status) {
        if (status == null) {
            return null;
        } else if (status) {
            return ntsTax.isPaymentWritten.isTrue();
        } else {
            return ntsTax.isPaymentWritten.isFalse();
        }
    }

    private BooleanExpression betweenCsStatus(ValidStatus status) {
        if (status == null) {
            return null;
        } else {
            return ntsTax.validStatus.eq(status);
        }
    }
}
