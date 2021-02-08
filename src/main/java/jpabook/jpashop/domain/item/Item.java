package jpabook.jpashop.domain.item;


import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();


    /*
    객체 지향의 입장에서 밨을 때, 변수(data)를 가지고 있는 쪽에서 비즈니스 메소드가 있는 것이 응집력이 좋다.
    -> stockQuantity 를 서비스에서 바꾸는 것이 아니라 entity 안에서 값을 변경하고 하는 것이 좋다 .
     */

    // 비즈니스 로직 //
    public void addStock(int quantity){  // 재고 증가
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity){
        int restStock = this.stockQuantity-quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }



}
