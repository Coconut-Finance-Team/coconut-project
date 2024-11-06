package com.coconut.stock_app.service;

import com.coconut.stock_app.config.ApiConfig;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class StockService {
    private final ApiConfig apiConfig;
    private final TokenService tokenService;
    private final RestTemplate restTemplate;

    @Autowired
    public StockService(ApiConfig apiConfig, TokenService tokenService) {
        this.apiConfig = apiConfig;
        this.tokenService = tokenService;
        this.restTemplate = new RestTemplate();
    }

    // StockService.java
    public Map<String, Object> getKOSPIIndex() {
        System.out.println("StockService: getKOSPIIndex called"); // 로그 추가
        String accessToken = tokenService.getAccessToken();
        System.out.println("Access Token: " + accessToken); // Access Token 확인
        return getIndex("0001");  // 코스피 종목 코드
    }

    public Map<String, Object> getKOSDAQIndex() {
        return getIndex("1001");  // 코스닥 종목 코드
    }

    private Map<String, Object> getIndex(String inputIscd) {
        // API 엔드포인트 URL 설정
        String url = UriComponentsBuilder.fromHttpUrl(
                        apiConfig.getApiUrl() + "/uapi/domestic-stock/v1/quotations/inquire-index-price")
                .queryParam("FID_COND_MRKT_DIV_CODE", "U") // 시장 분류 코드
                .queryParam("FID_INPUT_ISCD", inputIscd) // 종목 코드 (코스피: 0001, 코스닥: 1001)
                .toUriString();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("authorization", "Bearer " + tokenService.getAccessToken());
        headers.set("appkey", apiConfig.getAppKey());
        headers.set("appsecret", apiConfig.getAppSecret());
        headers.set("tr_id", "FHPUP02100000");  // 트랜잭션 ID

        // 요청 전송 및 응답 처리
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, request, Map.class);

        return response.getBody();
    }
}