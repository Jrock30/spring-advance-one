package com.jrock.springadvanceone.trace.template;

import com.jrock.springadvanceone.trace.TraceStatus;
import com.jrock.springadvanceone.trace.logtrace.LogTrace;

/**
 * Template Method Pattern
 * 반환 타입을 객체 생성시점으로 미룸 T
 */
public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    // 생성자 주입 @Autowired 또는 생략하고 @RequiredArgsConstructor
    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {
        TraceStatus status = null;

        try {
            status = trace.begin(message);

            // 변하는 로직 호출, 상속 받아서 구현 하게끔.(추상화)
            T result = call();

            trace.end(status);
            return result;
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();

}
