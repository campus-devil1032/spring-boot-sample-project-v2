package com.example.campusspringdemov2.infra.bucket4j.interceptor;

import com.example.campusspringdemov2.global.response.normal.Response2xx;
import com.example.campusspringdemov2.global.response.normal.ResponseCode;
import com.example.campusspringdemov2.infra.bucket4j.service.PricingPlanService;
import com.google.gson.Gson;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

/**
 * Api Throttling 관련 클래스
 * 이 설정은 {@link com.campus.demo.apiserver.ApiServerApplication} 으로부터 호출 설정 되었다.
 */
@Component
public class RateLimitInterceptor implements HandlerInterceptor {

    private static final String HEADER_API_KEY = "apiKey";
    private static final String HEADER_LIMIT_REMAINING = "X-Rate-Limit-Remaining";
    private static final String HEADER_RETRY_AFTER = "X-Rate-Limit-Retry-After-Seconds";

    @Autowired
    private PricingPlanService pricingPlanService;

    /**
     * Mapping 되어있는 API RESOURCE에 접근 하기 전 이 함수를 통해 Bucket에 있는 Api Quota를 먼저 확인한다.
     * 접근 할 수 있는 토큰이 유효 한 경우 (접근 가능 횟수가 남아있는 경우)
     * 현재 구현 기준 ${{@link com.campus.demo.apiserver.domain.trade.api.TradeApi}} 에 맵핑 되어있는 코드로 핸들러가 넘어간다
     *
     * @param request  : intercept 한 servlet 객체
     * @param response : intercept 한 servlet 객체
     * @param handler  : intercept 한 servlet 객체
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String apiKey = request.getHeader(HEADER_API_KEY);

        if (apiKey == null || apiKey.isEmpty()) {
            makeResponseApiKeyNull(response);
            return false;
        }

        Bucket tokenBucket = pricingPlanService.resolveBucket(apiKey);
        ConsumptionProbe probe = tokenBucket.tryConsumeAndReturnRemaining(1);

        if (probe.isConsumed()) {
            /* response intercepting 후 헤더에 요청 가능횟수 필드 추가 */
            response.addHeader(HEADER_LIMIT_REMAINING, String.valueOf(probe.getRemainingTokens()));
            return true;

        } else {
            /*  */
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;

            makeResponseApiRequestBlock(response, waitForRefill, String.valueOf(probe.getRemainingTokens()));
            return false;
        }
    }

    /**
     * 'apiKey' 헤더가 없는 경우 호출. response 429 내보낸 후 통신 종료
     *
     * @param response : servlet response object
     * @throws IOException
     */
    private void makeResponseApiKeyNull(HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        byte[] bytesForResponse = gson.toJson(
                        Response2xx.of(
                                ResponseCode.BUCKET4J_APIKEY_NULL,
                                Response2xx.FieldReason.of(
                                        "header", HEADER_RETRY_AFTER, ResponseCode.BUCKET4J_APIKEY_NULL.getMessage())))
                .getBytes();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        ServletOutputStream pw = response.getOutputStream();
        pw.write(bytesForResponse);
        pw.flush();
        pw.close();
    }


    /**
     * API 요청량 초과 시 이 함수를 이용해 response를 보내고 통신 종료.
     *
     * @param response       : servlet response object
     * @param waitForRefill  : 버킷 재충전까지 남은 시간
     * @param remainingToken : 현재 남아있는 토큰의 갯수 (여기선 무조건 0임)
     * @throws IOException
     */
    private void makeResponseApiRequestBlock(HttpServletResponse response, long waitForRefill, String remainingToken) throws IOException {
        Gson gson = new Gson();
        byte[] bytesForResponse = gson.toJson(
                        Response2xx.of(
                                ResponseCode.BUCKET4J_APIKEY_QUOTA_OVER,
                                Response2xx.FieldReason.of(
                                        "header", HEADER_RETRY_AFTER, ResponseCode.BUCKET4J_APIKEY_QUOTA_OVER.getMessage())))
                .getBytes();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setHeader(HEADER_RETRY_AFTER, String.valueOf(waitForRefill));
        response.addHeader(HEADER_LIMIT_REMAINING, remainingToken);

        ServletOutputStream pw = response.getOutputStream();
        pw.write(bytesForResponse);
        pw.flush();
        pw.close();
    }
}