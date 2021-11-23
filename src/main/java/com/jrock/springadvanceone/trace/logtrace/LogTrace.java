package com.jrock.springadvanceone.trace.logtrace;

import com.jrock.springadvanceone.trace.TraceStatus;

/**
 * LogTrace 인터페이스는 로그 추적기를 위한 최소한의 기능인 아래의 세개의 메서드를 정의 했다.
 */
public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);

}
