package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

//    @Autowired  //생성자 injection
//    public MemberService(MemberRepository memberRepository) {
// testcase 에서 작성할 때, 직접 주입을 해줘야한다. 안놓치고 잘 생성할 수 있다. 명시적인 코드  최신 spring은 생성자가 하나만 있다면, 자동으로 injection해준다.
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional
    public Long join(Member member) {

            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();

        }

    private void validateDuplicateMember(Member member) {
            // 중복회원 조회
            // error 시 exception

        List<Member> findMembers= memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw  new IllegalStateException("Already Exist User");
        }
    }
    //회원 전체 조회
    @Transactional(readOnly = true)
    public List<Member> findMembers(){
            return memberRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Member findOne(Long id){
            return memberRepository.findOne(id);
    }

    }
