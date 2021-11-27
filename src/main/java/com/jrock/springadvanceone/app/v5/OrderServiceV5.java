package com.jrock.springadvanceone.app.v5;

import com.jrock.springadvanceone.trace.callback.TraceTemplate;
import com.jrock.springadvanceone.trace.logtrace.LogTrace;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate template;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace trace) {
        this.orderRepository = orderRepository;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId) {
        template.execute("OrderService.request()", () -> {
            orderRepository.save(itemId);
            return null; // 제네릭 사용하면 리턴 또한 null
        });
    }
}
