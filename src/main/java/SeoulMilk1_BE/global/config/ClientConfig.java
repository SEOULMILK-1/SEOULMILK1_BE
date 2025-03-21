package SeoulMilk1_BE.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientConfig {
    @Bean
    public RestClient restClient(RestClient.Builder builder) {
        return builder.build();
    }
}
