package SeoulMilk1_BE.nts_tax.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Nts_Tax", description = "세금계산서 관련 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/tax")
public class NtsTaxController {
}
