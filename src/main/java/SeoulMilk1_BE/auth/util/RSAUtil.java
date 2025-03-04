package SeoulMilk1_BE.auth.util;

import SeoulMilk1_BE.global.apiPayload.code.status.ErrorStatus;
import SeoulMilk1_BE.global.apiPayload.exception.GeneralException;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAUtil {
    /**
     * RSA 암호화
     **/
    public String encryptRSA(String password, String publicKey){
        try {
            PublicKey publicKeyFromString = getPublicKeyFromString(publicKey);

            // PEM 형식의 공개키 문자열을 Base64로 디코딩
            // RSA 암호화 수행
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKeyFromString);  // 암호화 모드로 초기화

            byte[] encryptedPassword = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8)); // 비밀번호 암호화

            // Base64로 인코딩하여 출력 (문자열로 안전하게 전달)
            return Base64.getEncoder().encodeToString(encryptedPassword);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.ENCRYPTION_FAILED);
        }
    }

    private PublicKey getPublicKeyFromString(String publicKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(publicKey);

        // X.509 포맷으로 공개키 복원
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }
}
