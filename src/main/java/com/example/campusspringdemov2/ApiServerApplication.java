package com.example.campusspringdemov2;

import com.example.campusspringdemov2.global.config.CustomProperties;
import com.example.campusspringdemov2.infra.bucket4j.interceptor.RateLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 디렉터리 구조 - 도메인을 기준으로 디렉터리를 짰습니다.
 * 확장성 있는 개발, 코드 재사용성 및 객체의 재사용성을 중심으로 개발했습니다.
 * <p>
 * API 개발에서 중요한 기본 원칙들은 준수 하려고 노력합니다.
 * <p>
 * API 디자인 할 때 개인적인 철학은 아래와 같습니다.
 * - 재사용성 높은 코드
 * - 일관성있는 응답
 * - 개발 실수를 줄이기 위한 데이터모델 구조화 (ex. response message 직렬화)
 * <p>
 * 물론 API 디자인 관련 기술적인 관점은 best practice가 많지만,
 * 그런것보다도 그 것을 어떻게 개발해서 쌓아 올리느냐, 그리고 그렇게 개발 한 것이
 * 확장성있게 개발 하는 과정에서 생산성에 얼마나 도움이 되느냐 라는 철학이 더 중요하다고 봅니다.
 * <p>
 * 왜냐면, 빠르게 만들 수록 실패 및 피드백이 더 빨라지는게 가능해지고
 * 이 것이 가능하다면 더욱 빠르게 멋진 서비스를 만들 수 있다는 것을 믿기 때문입니다.
 */

@SpringBootApplication
@EnableWebMvc
public class ApiServerApplication implements WebMvcConfigurer {

    @Autowired
    @Lazy
    private RateLimitInterceptor rateLimitInterceptor;

    @Autowired
    CustomProperties customProperties;

    /**
     * @param registry : Api Throttling 관련 요청을 전처리 하기 위해 인터셉터를 이 registry를 통해 추가한다.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /* Api Resource는 버전별로 지정 */
        registry.addInterceptor(rateLimitInterceptor)
                .addPathPatterns("/" + customProperties.getApiVersionV1() + "/trade/date/**")
                .addPathPatterns("/" + customProperties.getApiVersionV2() + "/trade/date/**")
                .addPathPatterns("/" + customProperties.getApiVersionV3() + "/trade/date/**")
                .addPathPatterns("/" + customProperties.getApiVersionV4() + "/trade/date/**")
                .addPathPatterns("/" + customProperties.getApiVersionV5() + "/trade/date/**");
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ApiServerApplication.class)
                .properties("spring.config.location=classpath:application-starter.yml")
                .run(args);
    }
}
