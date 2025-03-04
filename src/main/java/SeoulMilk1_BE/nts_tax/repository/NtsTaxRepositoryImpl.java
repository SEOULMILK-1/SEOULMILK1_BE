package SeoulMilk1_BE.nts_tax.repository;

import SeoulMilk1_BE.nts_tax.domain.type.Status;
import SeoulMilk1_BE.nts_tax.dto.response.CsSearchTaxResponse;
import SeoulMilk1_BE.nts_tax.dto.response.HqSearchTaxResponse;
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

@Repository
@RequiredArgsConstructor
public class NtsTaxRepositoryImpl implements NtsTaxRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<HqSearchTaxResponse> findTaxUsedInHQ(Pageable pageable, String keyword, String startDate, String endDate, Long months, Status status) {
        List<HqSearchTaxResponse> results = queryFactory.select(
                        Projections.constructor(HqSearchTaxResponse.class,
                                ntsTax.id,
                                Expressions.stringTemplate("CONCAT(ntsTax.suDeptName, ' ', SUBSTRING(issueDate, 3, 2), '년 ', SUBSTRING(issueDate, 5, 2), '월 세금계산서')").as("formattedTitle"),
                                Expressions.stringTemplate("CONCAT(SUBSTRING(issueDate, 1, 4), '.', SUBSTRING(issueDate, 5, 2), '.', SUBSTRING(issueDate, 7, 2))").as("formattedIssueDate"),
                                ntsTax.suDeptName,
                                ntsTax.suPersName,
                                ntsTax.status
                        )
                )
                .from(ntsTax)
                .where(containsKeyword(keyword),
                        betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenStatus(status))
                .offset((pageable.getOffset()))
                .orderBy(ntsTax.issueDate.desc())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(ntsTax.id)
                .from(ntsTax)
                .where(containsKeyword(keyword),
                        betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenStatus(status));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }


    @Override
    public Page<CsSearchTaxResponse> findTaxUsedInCS(Pageable pageable, Long userId, String startDate, String endDate, Long months, Status status) {
        List<CsSearchTaxResponse> results = queryFactory.select(
                        Projections.constructor(CsSearchTaxResponse.class,
                                ntsTax.id,
                                Expressions.stringTemplate("CONCAT(ntsTax.suDeptName, ' ', SUBSTRING(issueDate, 3, 2), '년 ', SUBSTRING(issueDate, 5, 2), '월 세금계산서')").as("formattedTitle"),
                                Expressions.stringTemplate("CONCAT(SUBSTRING(issueDate, 1, 4), '.', SUBSTRING(issueDate, 5, 2), '.', SUBSTRING(issueDate, 7, 2))").as("formattedIssueDate"),
                                ntsTax.suDeptName,
                                ntsTax.suPersName,
                                ntsTax.status
                        )
                )
                .from(ntsTax)
                .where(equalUser(userId),
                        betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenStatus(status))
                .offset((pageable.getOffset()))
                .orderBy(ntsTax.issueDate.desc())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(ntsTax.id)
                .from(ntsTax)
                .where(betweenDate(startDate, endDate),
                        betweenMonths(months),
                        betweenStatus(status));

        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }


    private BooleanExpression containsKeyword(String keyword) {
        return StringUtils.hasText(keyword) ? ntsTax.suDeptName.containsIgnoreCase(keyword) : null;
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

    private BooleanExpression betweenStatus(Status status) {
        if (status != null) {
            return ntsTax.status.eq(status);
        }

        return ntsTax.status.eq(Status.APPROVE);
    }
}
