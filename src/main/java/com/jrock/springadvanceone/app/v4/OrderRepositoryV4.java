package com.jrock.springadvanceone.app.v4;

import com.jrock.springadvanceone.trace.TraceStatus;
import com.jrock.springadvanceone.trace.logtrace.LogTrace;
import com.jrock.springadvanceone.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {

    private final LogTrace trace;

    public void save(String itemId) {

        /**
         * Template Method Pattern
         *   - 익명 내부클래스를 사용하지 않고 별도로 테스트 코드처럼 상속받아 사용해도 된다.
         *   - T 제네릭을 사용하게 되면 void 타입은 직접 Void 라고 입력해 주어야한다.
         */
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                // 저장로직
                if (itemId.equals("ex")) {
                    throw new IllegalStateException("예외 발생");
                }
                sleep(1000);
                return null; // 제네릭 사용하면 리턴 또한 null
            }
        };
        template.execute("OrderRepository.request()");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
