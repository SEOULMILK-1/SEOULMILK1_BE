package SeoulMilk1_BE.nts_tax.service;

import SeoulMilk1_BE.nts_tax.repository.NtsTaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NtsTaxService {
    private final NtsTaxRepository ntsTaxRepository;
}
