package jpabook.jpashop;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional   //test 끝나면 db rollback 처리
    @Rollback(false)
    public void testMember () throws Exception{
    //given
    Member member = new Member();
    member.setUsername("A");
        //when
        Long saveId = memberRepository.save(member);  //option +command + v ->변수뽑아오기
        Member findMember = memberRepository.find(saveId);

        //then
       assertThat(findMember.getId()).isEqualTo(member.getId());
       assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
       assertThat(findMember).isEqualTo(member);
        System.out.println(findMember == member);  //true
    }

}