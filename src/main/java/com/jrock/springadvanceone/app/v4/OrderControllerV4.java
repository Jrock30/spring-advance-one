package com.jrock.springadvanceone.app.v4;

import com.jrock.springadvanceone.trace.logtrace.LogTrace;
import com.jrock.springadvanceone.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Trace(로그 추적기) V4 Level 적용
 *   - Template Method Pattern 사용
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final LogTrace trace; // 싱글턴으로 하나의 인스턴스가 생성되기 떄문에 Controller, Service, Repository 모두 동일안 인스턴스를 공유한다.

    @GetMapping("/v4/request")
    public String request(String itemId) {

        /**
         * Template Method Pattern
         *   - 익명 내부클래스를 사용하지 않고 별도로 테스트 코드처럼 상속받아 사용해도 된다.
         */
        AbstractTemplate<String> template = new AbstractTemplate<>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        };
        return template.execute("OrderController.request()");
    }
}
