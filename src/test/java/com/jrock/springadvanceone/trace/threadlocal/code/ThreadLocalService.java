package com.jrock.springadvanceone.trace.threadlocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {
    private ThreadLocal<String> nameStore = new ThreadLocal<>(); // 쓰레드로컬 (쓰레드 로컬을 사용을 다하면 .remove 로 지워주는게 좋다 메모리 누스 발생

    public String logic(String name) {
        log.info("저장 name={} --> nameStore={}", name, nameStore.get());
        nameStore.set(name); // 쓰레드로컬 저장
        sleep(1000);
        log.info("조회 nameStore={}", nameStore.get());
        return nameStore.get(); // 쓰레드로컬 가져오기
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
