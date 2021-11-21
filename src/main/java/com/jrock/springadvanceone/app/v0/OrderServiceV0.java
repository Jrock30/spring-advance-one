package com.jrock.springadvanceone.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 기본 생성자 자동으로 만들어줌
public class OrderServiceV0 {
    private final OrderRepositoryV0 orderRepository;

//    @Autowired
//    public OrderService(OrderRepositoryV0 orderRepository) {
//        this.orderRepository = orderRepository;
//    }

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
