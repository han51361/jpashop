package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    //나는 주인이 아니에요 하면 mappedby  order table 에있는 member에 맵핑
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
