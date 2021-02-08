package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em ;

    public void save(Item item){
        if(item.getId() ==null){
            // item 이 처음 jpa에 저장될 때까지는 id를 가지고 저장하지 않는다.
            em.persist(item);
        }else{
            em.merge(item); //merge == update
        }

    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i ", Item.class)
                .getResultList();
    }


}
