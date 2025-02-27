package SeoulMilk1_BE.nts_tax.service;

import SeoulMilk1_BE.global.service.S3Service;
import SeoulMilk1_BE.user.dto.response.OcrResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OcrService {

    @Value("${ocr.secretKey}")
    private String secret;

    @Value("${ocr.api-url}")
    private String apiUrl;

    @Value("${ocr.template-id}")
    private String templateIds;

    private final S3Service s3Service;

    public OcrResponse callApi(String type, MultipartFile multipartFile) {
        String imageUrl = s3Service.uploadFile(multipartFile);

        String apiURL = apiUrl;
        String secretKey = secret;
        String ext = getFileExtension(multipartFile);
        List<String> parseData = new ArrayList<>();

        log.info("callApi Start!");

        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setReadTimeout(30000);
            con.setRequestMethod(type);

            String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            con.setRequestProperty("X-OCR-SECRET", secretKey);

            // JSON 메시지 생성
            JSONObject json = new JSONObject();
            json.put("version", "V1"); // 버전 수정
            json.put("requestId", UUID.randomUUID().toString()); // UUID 생성
            json.put("timestamp", System.currentTimeMillis());

            JSONObject image = new JSONObject();
            image.put("format", ext);
            image.put("name", "seoulmilk");
            image.put("url", imageUrl);
            image.put("data", encodeFileToBase64(multipartFile));

            JSONArray templateArray = new JSONArray();
            for (String id : templateIds.split(",")) {
                templateArray.add(Integer.parseInt(id.trim())); // trim()으로 공백 제거
            }
            image.put("templateIds", templateArray);

            JSONArray images = new JSONArray();
            images.add(image);
            json.put("images", images);

            String jsonMessage = json.toString();

            // 연결 시작
            con.connect();
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                writeMultiPart(wr, jsonMessage, multipartFile, boundary);
            }

            int responseCode = con.getResponseCode();
            BufferedReader br = (responseCode == 200)
                    ? new BufferedReader(new InputStreamReader(con.getInputStream()))
                    : new BufferedReader(new InputStreamReader(con.getErrorStream()));

            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            log.info("OCR API Response: {}", response);

            // JSON을 OcrResponse 객체로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            OcrResponse ocrResponse = objectMapper.readValue(response.toString(), OcrResponse.class);
            return ocrResponse;

        } catch (Exception e) {
            log.error("Error during API call", e);
        }
        return new OcrResponse("error", "error", 0, null);
    }

    private static void writeMultiPart(OutputStream out, String jsonMessage, MultipartFile file, String boundary) throws IOException {
        String lineEnd = "\r\n";

        // JSON 데이터 전송
        StringBuilder sb = new StringBuilder();
        sb.append("--").append(boundary).append(lineEnd);
        sb.append("Content-Disposition: form-data; name=\"message\"").append(lineEnd);
        sb.append("Content-Type: application/json; charset=UTF-8").append(lineEnd).append(lineEnd);
        sb.append(jsonMessage).append(lineEnd);
        out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        out.flush();

        // 파일 데이터 전송
        if (!file.isEmpty()) {
            out.write(("--" + boundary + lineEnd).getBytes(StandardCharsets.UTF_8));
            String header = "Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getOriginalFilename() + "\"" + lineEnd +
                    "Content-Type: " + file.getContentType() + lineEnd + lineEnd;
            out.write(header.getBytes(StandardCharsets.UTF_8));
            out.flush();

            try (InputStream fileInputStream = file.getInputStream()) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.write(lineEnd.getBytes(StandardCharsets.UTF_8));
            }
        }

        // 바운더리 종료
        out.write(("--" + boundary + "--" + lineEnd).getBytes(StandardCharsets.UTF_8));
        out.flush();
    }

    public String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            // 마지막 '.'의 위치를 찾아서 그 뒤의 문자열을 반환
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }
        return ""; // 확장자가 없을 경우 빈 문자열 반환
    }

    private String encodeFileToBase64(MultipartFile file) throws IOException {
        byte[] fileContent = file.getBytes();
        return Base64.getEncoder().encodeToString(fileContent);
    }
}