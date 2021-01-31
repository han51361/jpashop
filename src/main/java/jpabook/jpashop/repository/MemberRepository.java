package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext // JPA가 제공하는 어노테이션
    private EntityManager em;  // spring 이 entity manager 를 만들어서 얘를 자동으로 주입해준다.

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
