package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; //springboot jpa build에 자동으로 등록되어잇다.

    public Long save(Member member){ // save 하는 코드
        em.persist(member);
        return member.getId(); //id만 리턴하는 이유 :  커맨드와 쿼리를 분류하라라는 원칙

    }


    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
