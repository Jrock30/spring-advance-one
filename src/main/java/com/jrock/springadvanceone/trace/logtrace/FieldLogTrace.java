package com.jrock.springadvanceone.trace.logtrace;

import com.jrock.springadvanceone.trace.TraceId;
import com.jrock.springadvanceone.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * 빈으로 등록할 LogTrace (동시성 문제)
 *   - 현재 이 것은 동시성 문제가 발생한다. 예를들어 순식간에 여러번 호출하게 되면 트랜잭션 ID가 동일하고 레벨 또한 이상하게 더 늘어난다.
 *   ** 이유는 FieldLogTrace 는 실글톤으로 등록된 스프링 빈이다. 이 객체의 인스턴스가 애플리케이션에 딱 1개 존재한다는 뜻이다.
 *      이렇게 하나만 있는 인스턴스의 FieldLogTrace.traceIdHolder 필드를 여러 쓰레드가 동시에 접근하기 때문에 문제가 발생한다.
 *
 */
@Slf4j
public class FieldLogTrace implements LogTrace {

    private static final String START_PREFIX = "--->";
    private static final String COMPLETE_PREFIX = "<---";
    private static final String EX_PREFIX = "<X-";

    private TraceId traceIdHolder; // traceId 동기화, 동시성 이슈 발생

    /**
     * 로그 시작 메서드
     */
    @Override
    public TraceStatus begin(String message) {
        syncTraceId(); // 여기서 traceId 를 만든다.
        TraceId traceId = traceIdHolder;
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message); // 로그 출력
    }

    /**
     * SyncTraceId
     *  - 즉 시작점 마다 레벨을 + 1 시킨다.
     */
    private void syncTraceId() {
        if (traceIdHolder == null) {
            traceIdHolder = new TraceId();
        } else {
            traceIdHolder = traceIdHolder.createNextId();
        }
    }

    /**
     * 로그 종료 메서드
     */
    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    /**
     * 정상처리 될 떄는 End를 하면 되나 예외가 발생하면 exception을 호출
     */
    @Override
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

        releaseTraceId();
    }

    /**
     * releaseTraceId
     *   - 트랜잭션 로그레벨이 하나 끝날 때 마다 레벨을 -1 한다.
     */
    private void releaseTraceId() {
        if (traceIdHolder.isFirstLevel()) {
            traceIdHolder = null; // destroy
        } else {
            traceIdHolder = traceIdHolder.createPreviousId();
        }
    }

    /**
     * 레벨 depth 표현
     * level 이 ++ 되었을 때 prefix 를 append
     *
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
