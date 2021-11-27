package com.jrock.springadvanceone;

import com.jrock.springadvanceone.trace.callback.TraceCallback;
import com.jrock.springadvanceone.trace.callback.TraceTemplate;
import com.jrock.springadvanceone.trace.logtrace.FieldLogTrace;
import com.jrock.springadvanceone.trace.logtrace.LogTrace;
import com.jrock.springadvanceone.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ComponentScan 대상 (Singleton)
 */
@Configuration
public class LogTraceConfig {

    /**
     * 직접 빈 생성
     * @Configuration 아래 @Bean을 따로 만들지 않고 @Component 로 만들어도 된다.
     */
    @Bean
    public LogTrace logTrace() {
//        return new FieldLogTrace();
        return new ThreadLocalLogTrace(); // 쓰레드 로컬로 의존관계 주입, 싱글톤 빈 때문에 여기만 바꾸면 됨
    }
}
