package com.jrock.springadvanceone.trace;

import java.util.UUID;

/**
 * 트랜잭션 ID와 깊이를 표현하는 level을 묶어서 TraceID 라는 개념을 만듬.
 */
public class TraceId {

    private String id;
    private int level;

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8); // UUID 8자리만 사용(로그용이니 중복이 되더라도 크게 의미가 없다)
    }

    public TraceId createNextId() {
        return new TraceId(id, level + 1); // 로그 레벨 증가를 위한
    }

    public TraceId createPreviousId() {
        return new TraceId(id, level - 1); // 로그 레벨 감소를 위한
    }

    public Boolean isFirstLeve() {
        return level == 0; // 첫번째 레벨 여부
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
