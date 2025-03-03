package SeoulMilk1_BE.nts_tax.service;

import SeoulMilk1_BE.auth.util.RSAUtil;
import SeoulMilk1_BE.nts_tax.dto.request.CodefApiRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.codef.api.EasyCodefConstant;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @Value("${codef.client-id}")
    private String clientId;
    @Value("${codef.client-secret}")
    private String clientSecret;
    @Value("${codef.client-pfx}")
    private String certFile;
    @Value("${codef.client-password}")
    private String password;
    @Value("${codef.client-publickey}")
    private String publicKey;
    private final RSAUtil rsaUtil;

    private static final URL url;

    /**
     제3자 발급 사실 조회 OpenAPI URL 세팅
     **/
    static {
        try {
            url = new URL("https://development.codef.io/v1/kr/public/nt/third-party/tax-invoice-issue");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public String validateNtsTax(CodefApiRequest request) {
        String accessToken = (String) publishToken().get("access_token");
        String result = getIssuedTaxInvoiceInfo(accessToken, request);
        return extractResult(result);
    }

    /**
     * 결과 값 디코딩
     **/
    private String extractResult(String result) {
        try {
            String decode = URLDecoder.decode(result, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(decode);
            return jsonNode.path("result").path("message").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Access Token 발행
     **/
    private HashMap publishToken() {
        BufferedReader br = null;
        try {
            // HTTP 요청을 위한 URL 오브젝트 생성
            URL url = new URL(EasyCodefConstant.OAUTH_DOMAIN + EasyCodefConstant.GET_TOKEN);
            String params = "grant_type=client_credentials&scope=read";    // Oauth2.0 사용자 자격증명 방식(client_credentials) 토큰 요청 설정

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // 클라이언트아이디, 시크릿코드 Base64 인코딩
            String auth = clientId + ":" + clientSecret;
            byte[] authEncBytes = Base64.encodeBase64(auth.getBytes());
            String authStringEnc = new String(authEncBytes);
            String authHeader = "Basic " + authStringEnc;

            con.setRequestProperty("Authorization", authHeader);
            con.setDoInput(true);
            con.setDoOutput(true);

            // 리퀘스트 바디 전송
            OutputStream os = con.getOutputStream();
            os.write(params.getBytes());
            os.flush();
            os.close();

            // 응답 코드 확인
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {    // 정상 응답
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {     // 에러 발생
                return null;
            }

            // 응답 바디 read
            String inputLine;
            StringBuffer responseStr = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                responseStr.append(inputLine);
            }
            br.close();

            // 응답결과 URL Decoding(UTF-8)
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, Object> tokenMap = mapper.readValue(URLDecoder.decode(responseStr.toString(), "UTF-8"), new TypeReference<HashMap<String, Object>>() {
            });
            return tokenMap;

        } catch (Exception e) {
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * 기본 요청 값 세팅
     **/
    private JSONObject setRequestJsonObject(CodefApiRequest request) throws Exception {
        JSONObject jsonRequest = new JSONObject();
        jsonRequest.put("organization", "0004");

        // 사용자 관련
        jsonRequest.put("loginType", "0");
        jsonRequest.put("certType", "pfx");
        jsonRequest.put("certFile", certFile);
        jsonRequest.put("certPassword", rsaUtil.encryptRSA(password, publicKey));
        jsonRequest.put("supplierRegNumber", request.suId());
        jsonRequest.put("contractorRegNumber", request.ipId());
        jsonRequest.put("approvalNo", request.issueId());
        jsonRequest.put("reportingDate", request.transDate());
        jsonRequest.put("supplyValue", request.chargeTotal());

        return jsonRequest;
    }

    /**
     * 요청 헤더 세팅
     **/
    private static HttpURLConnection getHttpURLConnection(String accessToken, URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + accessToken);
        con.setDoOutput(true);
        return con;
    }

    /**
     * API 요청
     **/
    private String getIssuedTaxInvoiceInfo(String accessToken, CodefApiRequest request) {
        BufferedReader br = null;
        try {
            HttpURLConnection con = getHttpURLConnection(accessToken, url);


            JSONObject jsonRequest = setRequestJsonObject(request);

            byte[] jsonData = jsonRequest.toString().getBytes(StandardCharsets.UTF_8);

            try (OutputStream os = con.getOutputStream()) {
                os.write(jsonData);
            }

            // 응답 코드 확인
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            } else { // 오류 발생 시
                br = new BufferedReader(new InputStreamReader(con.getErrorStream(), "utf-8"));
            }

            // 응답 데이터 읽기
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }

            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
        }
    }
}
