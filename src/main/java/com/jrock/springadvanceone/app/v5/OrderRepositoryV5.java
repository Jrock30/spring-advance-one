package com.jrock.springadvanceone.app.v5;

import com.jrock.springadvanceone.trace.callback.TraceCallback;
import com.jrock.springadvanceone.trace.callback.TraceTemplate;
import com.jrock.springadvanceone.trace.logtrace.LogTrace;
import com.jrock.springadvanceone.trace.template.AbstractTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepositoryV5 {

    private final TraceTemplate template;

    public OrderRepositoryV5(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId) {

        template.execute("OrderRepository.request()", () -> {
            // 저장로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생");
            }
            sleep(1000);
            return null; // 제네릭 사용하면 리턴 또한 null
        });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
