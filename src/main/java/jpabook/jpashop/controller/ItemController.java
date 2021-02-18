package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form",new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm bookForm){
     Book book = new Book();
     book.setName(bookForm.getName());
     book.setPrice(bookForm.getPrice());
     book.setStockQuantity(bookForm.getStockQuantity());
     book.setAuthor(bookForm.getAuthor());
     book.setIsbn(bookForm.getIsbn());

     itemService.saveItem(book);
     return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }

    @GetMapping("/itmes/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form",form);
        return  "items/updateItemForm";
    }


    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        //준영속 엔티티 새로운 객체이긴 하나, JPA기반에서 봤을 떄 Db를 거쳐서 이미 id 가 셋팅되어있고 식별자가 존재함, 기존의 식별자를 가지고 있기에 준영속엔티티로 볼 수 있다.
        // 얘는 JPA 가 관리를 안한다. 따라서 아무리 값을 바꿔도 변경감지가 되지않아 자동 업데이트를 하지 않는다.


//        Book book = new Book();
//        book.setId(form.getId());
//        book.setPrice(form.getPrice());
//        book.setName(form.getName());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setAuthor(form.getAuthor());
//        book.setIsbn(form.getIsbn());
//        itemService.saveItem(book);

        itemService.updateItem(itemId,form.getName(),form.getPrice(),form.getStockQuantity());
        return "redirect:/items";
    }
}
