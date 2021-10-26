package com.backmin.domains.order.domain;

import com.backmin.domains.member.domain.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private Long id;

    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String requirement;

    private LocalDateTime requestAt;

    private LocalDateTime completeAt;

    @Enumerated(EnumType.STRING)
    private Payment payMent;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenu = new ArrayList<>();
}
