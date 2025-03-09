package SeoulMilk1_BE.payment_resolution.service;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import SeoulMilk1_BE.nts_tax.service.NtsTaxService;
import SeoulMilk1_BE.payment_resolution.domain.PaymentResolution;
import SeoulMilk1_BE.payment_resolution.dto.request.PaymentResolutionRequest;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionInsertResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionListResponse;
import SeoulMilk1_BE.payment_resolution.dto.response.PaymentResolutionReadResponse;
import SeoulMilk1_BE.payment_resolution.repository.PaymentResolutionRepository;
import SeoulMilk1_BE.payment_resolution.utils.PaymentResolutionConstants;
import SeoulMilk1_BE.user.service.TeamService;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentResolutionService {

    private final PaymentResolutionRepository paymentResolutionRepository;
    private final TeamService teamService;
    private final NtsTaxService ntsTaxService;

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
        context.setVariable("writeDate", paymentResolution.getModifiedAt());
        context.setVariable("docNumber", "1234567890");
        context.setVariable("recipient", "서울우유태평고객센터");
        context.setVariable("payer", "서울우유태평고객센터");
        context.setVariable("recipientBusinessNumber", "1029301924");
        context.setVariable("payerBusinessNumber", "1029301924");
        context.setVariable("totalAmount", "654,321,000");
        context.setVariable("approver", "문정욱");
        context.setVariable("paymentMethod", "계좌이체");
        context.setVariable("approvalDate", "2025.02.26");
        context.setVariable("bankAccount", "국민 1234567891234");
        context.setVariable("scheduledDate", "2025.03.09");

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
