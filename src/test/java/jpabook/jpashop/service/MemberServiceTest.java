package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;



import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    public void  회원가입() throws Exception{
        /*
        * insert 쿼리 없이 select 후 완료 된다.
        * -> 기본적으로 join method 에서 entitymanager에 persist를 한다. persist한다고 insert문이 나가질 않는다.
        * -> 이유 : database transaction 이 커밋될 때, 플러쉬가 되면서 db에 인서트 쿼리가 들어간다. 따라서 트랜잭션커밋이 중요하다.
        * spring에서 transactional 은 커밋을 안하고 rollbackdmf 하기 때문에 @rollback(false=true)를 설정해줘야 insert문이 나간다.
        * */

    //given
    Member member = new Member();
    member.setName("Han");

    //when
        Long savedId= memberService.join(member);

        //then

    assertEquals(member, memberRepository.findOne(savedId));
    }


 @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
    //given
    Member member1 = new Member();
    member1.setName("han");

        //given
        Member member2 = new Member();
        member2.setName("han");
    //when
    memberService.join(member1);
        memberService.join(member2); // 여기서 예외처리를 해주는 것을 처리해줘야 함.

    //then
        fail("Exception Appear");

    }


}