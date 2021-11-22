package com.jrock.springadvanceone.trace.hellotrace;

import com.jrock.springadvanceone.trace.TraceId;
import com.jrock.springadvanceone.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 싱글톤으로 컴포넌트 등록해서 사용.(컴포넌트 스캔 대상)
 */
@Slf4j
@Component
public class HelloTraceV1 {

    private static final String START_PREFIX = "--->";
    private static final String COMPLETE_PREFIX = "<---";
    private static final String EX_PREFIX = "<X-";
    /**
     * 시작할 떄 호출
     */
    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message); // 로그 출력
    }

    /**
     * 종료일 떄 호출
     */
    public void end(TraceStatus status) {
        complete(status, null);
    }

    /**
     * 정상처리 될 떄는 End를 하면 되나 예외가 발생하면 exception을 호출
     */
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    /**
     * 로그 종료 메서드
     */
    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs, e.toString());
        }
    }

    /**
     * 레벨 depth 표현
     * level=0
     * level=1 |--->
     * level=2      |--->
     *
     * level=2 ex |   |<X-
     * level=2 ex |<X-
     */
    private String addSpace(String prefix, int level) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < level; i++) {
            sb.append((i == level - 1) ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }
}
