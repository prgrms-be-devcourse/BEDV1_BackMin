package com.backmin.domains.order.domain;

import com.backmin.domains.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Order(Long id, String address, OrderStatus status, String requirement, LocalDateTime requestAt, LocalDateTime completeAt, Payment payMent, int totalPrice, Member member, List<OrderMenu> orderMenu) {
        this.id = id;
        this.address = address;
        this.status = status;
        this.requirement = requirement;
        this.requestAt = requestAt;
        this.completeAt = completeAt;
        this.payMent = payMent;
        this.totalPrice = totalPrice;
        this.member = member;
        this.orderMenu = orderMenu;
    }
}
