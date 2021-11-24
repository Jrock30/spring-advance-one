package com.jrock.springadvanceone.trace.template.code;

import lombok.extern.slf4j.Slf4j;

/**
 * Template Method Pattern
 * 변하지 않는 부분은 구현 클래스에 냅두고 변하는 부분은 상속 받아 구현한다.
 */
@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        call(); // 상속
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    /**
     * 변하는 부분
     */
    protected abstract void call();
}
