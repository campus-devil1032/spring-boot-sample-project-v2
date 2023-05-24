package com.example.campusspringdemov2.infra.bucket4j.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Refill;

import java.time.Duration;

/**
 * API 사용량을 조정 할 수 있다.
 * enum 내 bucketCapacity를 조정 해서 유저 등급별로 조정 할 수 있다.
 */
public enum PricingPlan {

    FREE(3), /* 예비 */

    BASIC(4), /* 예비 */

    PROFESSIONAL(10); /* 실질적으로 적용 되는 Api Throttling Quota 량은 이 부분으로 구현 함 */

    private int bucketCapacity;

    private PricingPlan(int bucketCapacity) {
        this.bucketCapacity = bucketCapacity;
    }

    Bandwidth getLimit() {
        return Bandwidth.classic(bucketCapacity, Refill.intervally(bucketCapacity, Duration.ofSeconds(10))); /* 10초 마다 버킷 리필*/
    }

    public int bucketCapacity() {
        return bucketCapacity;
    }

    static PricingPlan resolvePlanFromApiKey(String apiKey) {
        if (apiKey == null || apiKey.isEmpty()) { /* 예비 */
            return FREE;

        } else if (apiKey.startsWith("1676d4e6")) { /* apiKey 앞 부분을 사용함. 실제 사용 시에는 관련 APIKEY 모델링 필요함 */
            return PROFESSIONAL;

        } else if (apiKey.startsWith("BASIC-")) { /* 예비 */
            return BASIC;
        }
        return FREE;
    }
}
