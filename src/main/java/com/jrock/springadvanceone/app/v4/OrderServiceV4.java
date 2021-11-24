package com.jrock.springadvanceone.app.v4;

import com.jrock.springadvanceone.trace.TraceStatus;
import com.jrock.springadvanceone.trace.logtrace.LogTrace;
import com.jrock.springadvanceone.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 기본 생성자 자동으로 만들어줌
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        /**
         * Template Method Pattern
         *   - 익명 내부클래스를 사용하지 않고 별도로 테스트 코드처럼 상속받아 사용해도 된다.
         *   - T 제네릭을 사용하게 되면 void 타입은 직접 Void 라고 입력해 주어야한다.
         */
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null; // 제네릭 사용하면 리턴 또한 null
            }
        };
        template.execute("OrderService.request()");
    }
}
