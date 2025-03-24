package SeoulMilk1_BE.nts_tax.service;

import SeoulMilk1_BE.nts_tax.dto.request.CodefApiRequest;
import SeoulMilk1_BE.nts_tax.dto.request.CodefValidateRequest;
import SeoulMilk1_BE.nts_tax.util.CodefConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codef.api.EasyCodefConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class CodefService extends EasyCodefConstant {
    private final CodefConfigProperties properties;
    private final RestClient restClient;
    private final WebClient webClient;

    /**
     * Access Token 발행
     **/
    @Cacheable(value = "tokenCache")
    public String publishToken() {
        String auth = properties.getClient_id() + ":" + properties.getClient_secret();
        byte[] authEncBytes = Base64.encodeBase64(auth.getBytes());
        String authStringEnc = new String(authEncBytes);
        String authHeader = "Basic " + authStringEnc;

        return (String) restClient.post()
                .uri(OAUTH_DOMAIN + GET_TOKEN)
                .header("Authorization", authHeader)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("grant_type=client_credentials&scope=read")
                .retrieve()
                .body(HashMap.class)
                .get("access_token");
    }

    public Mono<String> getValidationResult(CodefApiRequest request) {
        String accessToken = "Bearer " + publishToken();
        return webClient.post()
                .uri("https://development.codef.io/v1/kr/public/nt/third-party/tax-invoice-issue")
                .header("Authorization", accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(CodefValidateRequest.from(properties, request))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> validateNtsTax(CodefApiRequest request) {
        return getValidationResult(request)
                .map(this::decodeResult)
                .map(jsonNode -> jsonNode.path("result").path("message").asText());
    }

    /**
     결과 값 디코딩
     **/
    private JsonNode decodeResult(String result) {
        try {
            String decode = URLDecoder.decode(result, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(decode);
            return jsonNode;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
