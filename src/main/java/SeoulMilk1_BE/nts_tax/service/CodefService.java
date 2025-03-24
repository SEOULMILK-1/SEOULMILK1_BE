package SeoulMilk1_BE.nts_tax.service;

import SeoulMilk1_BE.auth.util.RSAUtil;
import SeoulMilk1_BE.nts_tax.dto.request.CodefApiRequest;
import SeoulMilk1_BE.nts_tax.dto.request.CodefValidateRequest;
import SeoulMilk1_BE.nts_tax.util.CodefConfigProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codef.api.EasyCodefConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

//    /**
//     * 기본 요청 값 세팅
//     **/
//    private JSONObject setRequestJsonObject(CodefApiRequest request){
//        JSONObject jsonRequest = new JSONObject();
//        jsonRequest.put("organization", "0004");
//
//        // 사용자 관련
//        jsonRequest.put("loginType", "0");
//        jsonRequest.put("certType", "pfx");
//        jsonRequest.put("certFile", properties.getClient_pfx());
//        jsonRequest.put("certPassword", rsaUtil.encryptRSA(properties.getClient_password(), properties.getClient_publickey()));
//        jsonRequest.put("supplierRegNumber", request.suId());
//        jsonRequest.put("contractorRegNumber", request.ipId());
//        jsonRequest.put("approvalNo", request.issueId());
//        jsonRequest.put("reportingDate", request.issueDate());
//        jsonRequest.put("supplyValue", request.chargeTotal());
//
//        return jsonRequest;
//    }
//
//    /**
//     * 요청 헤더 세팅
//     **/
//    private static HttpURLConnection getHttpURLConnection(String accessToken, URL url) throws IOException {
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("POST");
//        con.setRequestProperty("Content-Type", "application/json");
//        con.setRequestProperty("Authorization", "Bearer " + accessToken);
//        con.setDoOutput(true);
//        return con;
//    }
//
//    /**
//     * API 요청
//     **/
//    private String getIssuedTaxInvoiceInfo(String accessToken, CodefApiRequest request) {
//        BufferedReader br = null;
//        try {
//            URL url = new URL(properties.getClient_url());
//            HttpURLConnection con = getHttpURLConnection(accessToken, url);
//
//
//            JSONObject jsonRequest = setRequestJsonObject(request);
//
//            byte[] jsonData = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);
//
//            try (OutputStream os = con.getOutputStream()) {
//                os.write(jsonData);
//            }
//
//            // 응답 코드 확인
//            int responseCode = con.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
//                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
//            } else { // 오류 발생 시
//                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
//            }
//
//            // 응답 데이터 읽기
//            StringBuilder response = new StringBuilder();
//            String inputLine;
//            while ((inputLine = br.readLine()) != null) {
//                response.append(inputLine);
//            }
//
//            return response.toString();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (br != null) {
//                try {
//                    br.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//
//                }
//            }
//        }
//    }
//
//    public String validateNtsTax(CodefApiRequest request) {
//        String accessToken = publishToken();
//        String result = getIssuedTaxInvoiceInfo(accessToken, request);
//        return extractResult(result);
//    }
//
//    /**
//     * 결과 값 디코딩
//     **/
//    private String extractResult(String result) {
//        try {
//            String decode = URLDecoder.decode(result, StandardCharsets.UTF_8);
//            ObjectMapper mapper = new ObjectMapper();
//            JsonNode jsonNode = mapper.readTree(decode);
//            return jsonNode.path("result").path("message").asText();
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

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
