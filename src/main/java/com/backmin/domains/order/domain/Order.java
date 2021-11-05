package com.backmin.domains.order.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.store.domain.Store;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@AllArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private OrderStatus status;

    @Column(name = "requirement", length = 100)
    private String requirement;

    @Column(name = "request_at", nullable = false)
    private LocalDateTime requestAt;

    @Column(name = "complete_at")
    private LocalDateTime completeAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment", nullable = false)
    private Payment payMent;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @Builder
    private Order(Long id,
                 String address,
                 OrderStatus status,
                 String requirement,
                 LocalDateTime requestAt,
                 LocalDateTime completeAt,
                 Payment payMent,
                 int totalPrice,
                 Member member,
                 Store store
    ) {
        this.id = id;
        this.address = address;
        this.status = status;
        this.requirement = requirement;
        this.requestAt = requestAt;
        this.completeAt = completeAt;
        this.payMent = payMent;
        this.totalPrice = totalPrice;
        this.member = member;
        this.store = store;
        this.orderMenus = new ArrayList<>();
    }

    public static Order of(String address, String requirement, Payment payment, Member member, int deliveryTip) {
        return Order.builder()
                .address(address)
                .requirement(requirement)
                .payMent(payment)
                .member(member)
                .requestAt(LocalDateTime.now())
                .status(OrderStatus.ACCEPTED)
                .totalPrice(deliveryTip)
                .build();
    }

    public static Order of(String address, String requirement, Payment payment, Member member, Store store, int deliveryTip) {
        return Order.builder()
                .address(address)
                .requirement(requirement)
                .payMent(payment)
                .member(member)
                .requestAt(LocalDateTime.now())
                .status(OrderStatus.ACCEPTED)
                .totalPrice(deliveryTip)
                .store(store)
                .build();
    }

    public void addOrderMenu(OrderMenu orderMenu) {
        orderMenu.changeOrder(this);
        this.totalPrice += orderMenu.getPrice() * orderMenu.getQuantity();
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.status = orderStatus;
    }

}
