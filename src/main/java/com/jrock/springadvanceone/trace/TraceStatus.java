package com.jrock.springadvanceone.trace;

/**
 * 로그를 시작하면 종료가 필요하다. 시작시간을 가지고 시간을 체크한다.(로그 상태정보)
 */
public class TraceStatus {

    private TraceId traceId;
    private Long startTimeMs;
    private String message; // 시작시 사용한 메시지. 이후 로그 종료시에도 이 메시지를 출력

    public TraceStatus(TraceId traceId, Long startTimeMs, String message) {
        this.traceId = traceId;
        this.startTimeMs = startTimeMs;
        this.message = message;
    }

    public TraceId getTraceId() {
        return traceId;
    }

    public Long getStartTimeMs() {
        return startTimeMs;
    }

    public String getMessage() {
        return message;
    }
}
