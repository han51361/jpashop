package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;


    @GetMapping("/members/new")
    public String createForm(Model model){
        model.addAttribute("memberForm", new MemberForm());  // html 의 th:Object와 연결
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid  MemberForm form, BindingResult result){
        // binding Result => 오류가 있다면, 튕기 지않고 오류가 담겨서 실행이 된다.

        if (result.hasErrors()){
            // 에러 발생시, 다시 화면으로 돌아가게 만들었다. @valid 로 인해 이름 미 필기시 @NotEmpty 에러 메세지 보임
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);

        return "redirect:/";  //첫번쨰 페이지로 넘어감
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

}
