package com.jrock.springadvanceone.app.v1;

import com.jrock.springadvanceone.trace.TraceStatus;
import com.jrock.springadvanceone.trace.hellotrace.HelloTraceV1;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 기본 생성자 자동으로 만들어줌
public class OrderServiceV1 {
    private final OrderRepositoryV1 orderRepository;
    private final HelloTraceV1 trace;

//    @Autowired
//    public OrderService(OrderRepositoryV0 orderRepository) {
//        this.orderRepository = orderRepository;
//    }

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderService.request()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외 꼭 다시 던져주어야 한다. 로그는 어플리케이션 흐음에 영향을 주면 안된다.
        }
    }
}
