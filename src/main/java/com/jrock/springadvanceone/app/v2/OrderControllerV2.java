package com.jrock.springadvanceone.app.v2;

import com.jrock.springadvanceone.trace.TraceStatus;
import com.jrock.springadvanceone.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Trace(로그 추적기) V2 Level 적용
 */
@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "OK";
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외 꼭 다시 던져주어야 한다. 로그는 어플리케이션 흐음에 영향을 주면 안된다.
        }
    }
}
