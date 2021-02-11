package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    
    
    @Autowired
    private final EntityManager entityManager;



    public void save(Order order){
        entityManager.persist(order);
    }

    public Order findOne(Long id){
        return entityManager.find(Order.class, id);
    }

//    public List<Order> findAll(OrderSearch orderSearch){
//        return entityManager.createQuery( "select o from Order o", Order.class)
//                .getResultList();
//    }


}
