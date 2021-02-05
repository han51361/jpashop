package jpabook.jpashop.service;


import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;



    // 해당 회원 주문 조회

  //  @Transactional(readOnly = true)
   // public List<Order> findOrder(Long id){

   // }
}
