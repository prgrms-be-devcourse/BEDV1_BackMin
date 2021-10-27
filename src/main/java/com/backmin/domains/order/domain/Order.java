package com.backmin.domains.order.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.member.domain.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "requirement", length = 100)
    private String requirement;

    @Column(name = "request_at", nullable = false)
    private LocalDateTime requestAt;

    private LocalDateTime completeAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment", nullable = false)
    private Payment payMent;

    @Min(0)
    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder
    public Order(Long id, String address, OrderStatus status, String requirement, LocalDateTime requestAt, LocalDateTime completeAt,
            Payment payMent, int totalPrice, Member member, List<OrderMenu> orderMenu) {
        this.id = id;
        this.address = address;
        this.status = status;
        this.requirement = requirement;
        this.requestAt = requestAt;
        this.completeAt = completeAt;
        this.payMent = payMent;
        this.totalPrice = totalPrice;
        this.member = member;
        this.orderMenus = orderMenu;
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenu.changeOrder(this);
    }

}
