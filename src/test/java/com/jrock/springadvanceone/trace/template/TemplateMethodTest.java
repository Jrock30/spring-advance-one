package com.jrock.springadvanceone.trace.template;

import com.jrock.springadvanceone.trace.template.code.AbstractTemplate;
import com.jrock.springadvanceone.trace.template.code.SubClassLogic1;
import com.jrock.springadvanceone.trace.template.code.SubClassLogic2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * Template Method pattern
 */
@Slf4j
public class TemplateMethodTest {

    @Test
    public void templateMethodV0() throws Exception {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        log.info("비즈니스 로직1 실행");
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        // 비즈니스 로직 실행
        log.info("비즈니스 로직2 실행");
        // 비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        log.info("resultTime={}", resultTime);
    }

    /**
     * 템플릿 메서드 패턴 적용
     *   - 아래의 방법은 오버라이딩 받은 각 각 인스턴스를 만들어 주어야하는 단점이 있다. 이를 보완하는 것이 익명 내부 클래스틑 사용하는 것 이다.
     */
    @Test
    public void templateMethodV1() throws Exception {
        /**
         * 변하는 부분 call() 부분을 SubClassLogic1 에서 오버라이딩 구현되어있다.
         * 템플릿 메서드 패턴은 이렇게 다형성을 사용해서 변하는 부분과 변하지 않는 부분을 분리하는 방법이다.
         */
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();

        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    /**
     * 템플릿 메서드 패턴 적용
     * - 익명 내부 클래스 사용
     */
    @Test
    public void templateMethodV2() throws Exception {

        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        log.info("클래스 이름1={}", template1.getClass());
        template1.execute();

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        template2.execute();
        log.info("클래스 이름2={}", template2.getClass());
    }
}
