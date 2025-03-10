package SeoulMilk1_BE.payment_resolution.service;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.nts_tax.domain.NtsTax;
import SeoulMilk1_BE.nts_tax.service.NtsTaxService;
import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionRequest;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionUpdateAccountRequest;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionInsertResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionListResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionReadResponse;
import SeoulMilk1_BE.payment_resolution.repository.PaymentResolutionRepository;
import SeoulMilk1_BE.payment_resolution.utils.PaymentResolutionConstants;
import SeoulMilk1_BE.user.domain.User;
import SeoulMilk1_BE.user.service.TeamService;
import SeoulMilk1_BE.user.service.UserService;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentResolutionService {

    private final PaymentResolutionRepository paymentResolutionRepository;
    private final TeamService teamService;
    private final NtsTaxService ntsTaxService;
    private final UserService userService;

    @Transactional
    public PaymentResolutionInsertResponse createPaymentResolution(PaymentResolutionRequest request) {
        Optional<Integer> countResult = teamService.findAboutPayment(request.paymentRecipient(), LocalDateTime.now().getMonthValue());
        int count = 1;
        if (countResult != null) {
            count = countResult.get();
        }
        PaymentResolution paymentResolution = PaymentResolutionReadResponse.of(count, request);
        paymentResolutionRepository.save(paymentResolution);

        ntsTaxService.updatePaymentWritten(request.paymentDetails());

        return PaymentResolutionInsertResponse.of(paymentResolution.getId(), paymentResolution.getModifiedAt());
    }

    @Transactional
    public String createPaymentResolutionByGrouping(Long userId) {
        User user = userService.findUser(userId);
        List<NtsTax> ntsTaxList = ntsTaxService.findByPaymentWritten();

        if (ntsTaxList.size() == 0) {
            throw new GeneralException(ErrorStatus.NTS_TAX_NOT_FOUND);
        }
        // 작성되지 않은 세금계산서 목록 조회 및 지점별로 파티셔닝
        Map<String, List<NtsTax>> groupedByDept = ntsTaxList.stream().collect(Collectors.groupingBy(NtsTax::getSuDeptName));

        // 지점별로 지급결의서 작성
        groupedByDept.forEach((deptName, taxList) -> {
            PaymentResolutionRequest request = PaymentResolutionRequest.from(taxList, user);
            createPaymentResolution(request);
        });
        return PaymentResolutionConstants.CREATE_SUCCESS.getMessage();
    }

    public PaymentResolutionReadResponse readPaymentResolution(Long id) {
        PaymentResolution paymentResolution = paymentResolutionRepository.findById(id).orElseThrow(() -> new GeneralException(ErrorStatus.PAYMENT_RESOLUTION_NOT_FOUND));
        return PaymentResolutionReadResponse.byId(paymentResolution);
    }

    @Transactional
    public PaymentResolutionInsertResponse updatePaymentResolution(Long id, PaymentResolutionReadResponse request) {
        PaymentResolution paymentResolution = paymentResolutionRepository.findById(id).orElseThrow(() -> new GeneralException(ErrorStatus.PAYMENT_RESOLUTION_NOT_FOUND));
        paymentResolution.updatePaymentResolution(request);
        return PaymentResolutionInsertResponse.of(paymentResolution.getId(), paymentResolution.getModifiedAt());
    }

    @Transactional
    public String updateAccount(PaymentResolutionUpdateAccountRequest request) {
        PaymentResolution paymentResolution = paymentResolutionRepository.findById(request.id()).orElseThrow(() -> new GeneralException(ErrorStatus.PAYMENT_RESOLUTION_NOT_FOUND));
        paymentResolution.updateAccount(request);
        return PaymentResolutionConstants.UPDATE_ACCOUNT_SUCCESS.getMessage();
    }

    @Transactional
    public String deletePaymentResolution(Long id) {
        paymentResolutionRepository.deleteById(id);
        return PaymentResolutionConstants.DELETE_SUCCESS.getMessage();
    }

    public List<PaymentResolutionListResponse> readPaymentResolutionList(int period, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        List<PaymentResolution> result = paymentResolutionRepository.findListByDeadline(LocalDateTime.now().minusMonths(period), pageRequest).getContent();
        return result.stream().map(p -> PaymentResolutionListResponse.from(p)).collect(Collectors.toList());
    }

    private String parseThymeleafTemplate(PaymentResolution paymentResolution) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("title", paymentResolution.getName());
        context.setVariable("createdAt", paymentResolution.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        context.setVariable("docNumber", String.format("%010d", paymentResolution.getId()));
        context.setVariable("paymentRecepient", paymentResolution.getPaymentRecipient());
        context.setVariable("paymentPrincipal", paymentResolution.getPaymentPrincipal());
        context.setVariable("receipientBusinessNumber", paymentResolution.getRecipientBusinessNumber());
        context.setVariable("principalBusinessNumber", paymentResolution.getPrincipalBusinessNumber());
        context.setVariable("totalPaymentAccount", paymentResolution.getTotalPaymentAmount());
        context.setVariable("paymentMethod", paymentResolution.getPaymentMethod());
        context.setVariable("createdAt", paymentResolution.getModifiedAt().format(DateTimeFormatter.ofPattern("yyyy.MM.dd")));
        context.setVariable("paymentAccount", paymentResolution.getPaymentAccount());

        return templateEngine.process("payment_resolution", context);
    }

    private ByteArrayOutputStream generatePdfFromHtml(String html) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver()
                    .addFont(
                            new ClassPathResource("/templates/NanumBarunGothic.ttf")
                                    .getURL()
                                    .toString(),
                            BaseFont.IDENTITY_H,
                            BaseFont.EMBEDDED);
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            outputStream.close();
            return outputStream;
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.FAILED_TO_PDF);
        }
    }

    public byte[] createPdfFile(Long id) {
        PaymentResolution paymentResolution = paymentResolutionRepository.findById(id).orElseThrow(() -> new GeneralException(ErrorStatus.PAYMENT_RESOLUTION_NOT_FOUND));

        String paymentResolutionHtml = parseThymeleafTemplate(paymentResolution);
        ByteArrayOutputStream pdf = generatePdfFromHtml(paymentResolutionHtml);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", paymentResolution.getName() + ".pdf");

        return pdf.toByteArray();
    }
}
