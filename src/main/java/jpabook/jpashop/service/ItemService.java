package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class  ItemService {

    private  final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

    @Transactional
    public void updateItem(Long itemId,String name,int price,int stockQuantity){
        //변경감지 기능을 이용히여 준영속엔티티를 데이터수정하기
        //@transactionl 을 달아 마지막 커밋시점에서 변경감지 동작 하기
        //findItem 은 영속상태 따라서 save를 할 필요없이, JPA 는 커밋하면서 플러쉬를 날린다. 따라서 변경감지를 한다.
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(price);
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);


    }
}
