package jpabook.jpashop.service;


import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    // order가 orderItem, delivery 에 cascade가 걸려있기 때문에, persist를 날려준다.
    private final OrderRepository orderRepository;


    private final MemberRepository memberRepository;


    private final ItemRepository itemRepository;
    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count); //option + command  + v
        //setter를 사용하게 되면 , 나중에 유지보수하기가 힘들다.->  생성로직을 변경하기 힘들다.
        // 따라서 setter 생성을 막아야한다.


        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }
    // 취소
    @Transactional
    public void cancelOrder(Long orderId){
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소     안에 상세한 로직은 cancel 메소드에 다 들어가잇다.
        order.cancel();

    }

//    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAll(orderSearch);
//    }
}
