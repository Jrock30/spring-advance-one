package com.jrock.springadvanceone.app.v5;

import com.jrock.springadvanceone.trace.callback.TraceCallback;
import com.jrock.springadvanceone.trace.callback.TraceTemplate;
import com.jrock.springadvanceone.trace.logtrace.LogTrace;
import com.jrock.springadvanceone.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Template Callback Pattern
 *   this.template = new TraceTemplate(trace) : trace 의존관계 주입을 받으면서 필요한 TraceTemplate 템플릿을 생성한다. 참고로 TraceTemplate 를 처음부터 스프링 빈으로 등록하고 주입받아도 된다. 이 부분은 선택이다.
 *   template.execute(.., new TraceCallback(){..}) : 템플릿을 실행하면서 콜백을 전달한다. 여기서는 콜백으로 익명 내부 클래스를 사용했다.
 */
@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template; // 스프링 빈으로 등록해도 되지만 테스트할 때 좀 번거로울 수 있어서 이렇게 쓰는 것도 메모리에 영향 없으니 상관 없다.

//    @Autowired // 생성자 하나이니까 생략가능
    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        this.template = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId) {
        return template.execute("OrderController.request()", new TraceCallback<>() {
            @Override
            public String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        });
    }
}
