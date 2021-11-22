package com.jrock.springadvanceone.app.v2;

import com.jrock.springadvanceone.trace.TraceId;
import com.jrock.springadvanceone.trace.TraceStatus;
import com.jrock.springadvanceone.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private final HelloTraceV2 trace;

    public void save(TraceId traceId, String itemId) {

        TraceStatus status = null;
        try {
            status = trace.beginSync(traceId, "OrderRepository.request()");
            // 저장로직
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생");
            }
            sleep(1000);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e; // 예외 꼭 다시 던져주어야 한다. 로그는 어플리케이션 흐음에 영향을 주면 안된다.
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
