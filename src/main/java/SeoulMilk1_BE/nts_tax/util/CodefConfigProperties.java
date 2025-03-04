package SeoulMilk1_BE.nts_tax.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "codef")
@Data
public class CodefConfigProperties {
    private String client_id;
    private String client_secret;
    private String client_password;
    private String client_publickey;
    private String client_pfx;
    private String client_url;
}
