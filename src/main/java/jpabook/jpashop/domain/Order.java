package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name= "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)  //order를 Persist 걸어도 orderitems도 persist(저장)  된다.

    private List<OrderItem> orderItems = new ArrayList<>();


    // access 가 많은 곳에 onetoone 일 경우 foreign key 를 둔다.
    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;  //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;    //주문상태

    // ==연관관계 메서드

    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

  public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
  }

  public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
  }

  // 생성 메소드
  // 주문을 생성할 때 모든 것을 완성 시킨다 . 응집력 업
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);

        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }


    // 비즈니스 로직
    /**
     *주문 취소
      */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품입니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }

    }

    // 전체 주문 가격 조회
    public  int getTotalPrice(){
        int totalPrice = orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
        /* 위와 같은 로직 => java 8 의 기
        *    for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }*/
        return  totalPrice;
    }


}
