package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {


    @Autowired //  (springboot)springdata jpa 가 지원 이를 통해 @requiredArgsConstructor 사용 가능 이를 통해 @PersisenceContext 대신 @autowired+ @RequiredArgsConstructor
 //   @PersistenceContext // JPA가 제공하는 어노테이션
    private EntityManager em;  // spring 이 entity manager 를 만들어서 얘를 자동으로 주입해준다.

    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Member member){
        em.persist(member);
    }

    public Member findOne(Long id ){
        return em.find(Member.class, id);
    }

    public List<Member> findAll(){

       return em.createQuery("select m from Member m", Member.class) // jPQuery Entity 객체를 대상으로 셀렉트한다.
            .getResultList();
    }

    public List<Member> findByName(String name){
        return em.createQuery("select  m from Member  m where m.name = :name",Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
