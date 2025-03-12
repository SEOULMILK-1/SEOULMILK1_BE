package SeoulMilk1_BE.payment_resolution.repository;

import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.domain.QPaymentResolution;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionFindListByOptionsResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionListResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static SeoulMilk1_BE.payment_resolution.domain.QPaymentResolution.paymentResolution;

@Repository
@RequiredArgsConstructor
public class PaymentResolutionRepositoryImpl implements PaymentResolutionRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    @Override
    public PaymentResolutionFindListByOptionsResponse findListByOptions(Pageable pageable, String suDeptName, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime deadline) {
        QPaymentResolution paymentResolution = QPaymentResolution.paymentResolution;
        BooleanBuilder builder = new BooleanBuilder();

        if (suDeptName != null) {
            builder.and(paymentResolution.paymentRecipient.eq(suDeptName));
        }
        if (startDateTime != null) {
            builder.and(paymentResolution.createdAt.goe(startDateTime));
        }
        if (endDateTime != null) {
            builder.and(paymentResolution.createdAt.loe(endDateTime));
        }
        if (deadline != null) {
            builder.and(paymentResolution.createdAt.goe(deadline));
        }

        return new PaymentResolutionFindListByOptionsResponse(countAll(builder), findAll(builder, pageable).getContent());
    }

    public Page<PaymentResolution> findAll(BooleanBuilder builder, Pageable pageable) {
        List<PaymentResolution> result = queryFactory
                .selectFrom(paymentResolution)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectFrom(paymentResolution)
                .where(builder)
                .fetchCount();
        return new PageImpl<>(result, pageable, total);
    }

    public Long countAll(BooleanBuilder builder) {
        return queryFactory.select(paymentResolution.count())
                .from(paymentResolution)
                .where(builder)
                .fetchOne();
    }
}
