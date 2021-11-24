package com.jrock.springadvanceone.trace.template.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 변하는 부분인 비즈니스 로직을 처리하는 자식클래스이다. 템플릿이 호출하는 대상인 call() 메서드를 오버라이딩 한다.
 */
@Slf4j
public class SubClassLogic1 extends AbstractTemplate {

    @Override
    protected void call() {
        log.info("비즈니스 로직1 실행");
    }
}
