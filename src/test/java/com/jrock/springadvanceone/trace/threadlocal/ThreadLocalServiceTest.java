package com.jrock.springadvanceone.trace.threadlocal;

import com.jrock.springadvanceone.trace.threadlocal.code.ThreadLocalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {

    private ThreadLocalService service = new ThreadLocalService();

    @Test
    void field() throws Exception {
        log.info("main start");
        Runnable userA = () -> service.logic("userA");
        Runnable userB = () -> service.logic("userB");

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        /**
         * 동시성문제
         *  - threadA 가 끝나지 않았는데 같은 인스턴스에 ThreadB가 접근하게 되어 인스턴스 필드가 B 로 바뀌게 된다. 동시성 문제
         *    이처럼 여러 쓰레드가 동시에 같은 인스턴스의 필드 값을 변경하면서 발생하는 문제를 동시성 문제라 한다.
         *    이런 동시성 문제는 여러 쓰레드가 같은 인스턴스의 필드에 접근해야 하기 때문에 트래픽이 적은 상황에서는 확율상 잘 나타나지 않고,
         *    트래픽이 점점 많아질수록 자주 발생한다.
         *    특히 스프링 빈처럼 싱글톤 객체의 필드를 변경하며 사용할 때 이러한 동시성 문제를 조심해야 한다.
         *
         * 참고
         *  - 이런 동시성 문제는 지역 변수에서는 발생하지 않는다. 지역변수는 쓰레드마다 각각 다른 메모리 영역이 할당된다.
         *    동시성 문제가 발생하는 곳은 같은 인스턴스의 필드(주로 싱글톤에서 자주 발생), 또는 static 같은 공용 필드에 접근할 떄 발생한다.
         *    동시성 문제는 값을 읽기만 하면 발생하지 않는다. 어디선가 값을 변경하기 때문에 발생한다.
         *
         * ** 해결방안 쓰레드 로컬(해당 쓰레드만 접근할 수 있는 특별한 저장소)
         * ** 한마디로 같은 인스턴스 변수를 변경 조회를 여기저기서 한다고 하여도 독립적인 인스턴스 변수를 별도로
         * ** 저장하기 때문에 동시성 문제를 막을 수 있다.
         *
         */
        threadA.start();
//        sleep(2000); // 동시성 문제 발생 X
        sleep(100); // 동시성 문제 발생 O
        threadB.start();
        sleep(3000); // 메인 쓰레드 종료 대기
        log.info("main Exit");

    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
