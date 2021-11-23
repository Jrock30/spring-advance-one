package com.jrock.springadvanceone.app.v3;

import com.jrock.springadvanceone.trace.TraceStatus;
import com.jrock.springadvanceone.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Trace(로그 추적기) V2 Level 적용
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace; // 싱글턴으로 하나의 인스턴스가 생성되기 떄문에 Controller, Service, Repository 모두 동일안 인스턴스를 공유한다.

    @GetMapping("/v3/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "OK";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외 꼭 다시 던져주어야 한다. 로그는 어플리케이션 흐음에 영향을 주면 안된다.
        }
    }
}
