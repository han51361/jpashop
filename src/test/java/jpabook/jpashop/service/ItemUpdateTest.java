package jpabook.jpashop.service;


import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;



    @Test
    public void updateTest() throws Exception{
    //given
        Book book = em.find(Book.class, 1L);
        //TX
    book.setName("asdasd");
    //Tx commit 변경점에 대해 자동적으로 DB 에 반영 -> Dirty Checking (변경감지)
    
    }
}
