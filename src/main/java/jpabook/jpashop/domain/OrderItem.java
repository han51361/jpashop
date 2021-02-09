package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자 새로 직접 생성 불가 제약( Setter를 사용할 수 없게끔 )제약을 protected로 걸어준다.
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;


    // 생성 메소드
    public static  OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        // 주문량 만큼 재고 줄이기
        item.removeStock(count);
        return orderItem;
    }

    // 비즈니스 로직
    public void cancel() {

        getItem().addStock(count);

    }

    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
