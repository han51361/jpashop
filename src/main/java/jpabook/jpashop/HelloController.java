package jpabook.jpashop;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping
    public String hello(Model model){
        model.addAttribute("data", "hello");
        return "hello";  //html 파일 화면의 data의 값에 전달
   }
}
