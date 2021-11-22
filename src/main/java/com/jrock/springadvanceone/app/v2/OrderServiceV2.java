package com.jrock.springadvanceone.app.v2;

import com.jrock.springadvanceone.trace.TraceId;
import com.jrock.springadvanceone.trace.TraceStatus;
import com.jrock.springadvanceone.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 기본 생성자 자동으로 만들어줌
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderService.request()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외 꼭 다시 던져주어야 한다. 로그는 어플리케이션 흐음에 영향을 주면 안된다.
        }
    }
}
