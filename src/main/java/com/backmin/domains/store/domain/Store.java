package com.backmin.domains.store.domain;

import com.backmin.domains.common.BaseEntity;
import com.backmin.domains.member.domain.Member;
import com.backmin.domains.menu.domain.Menu;
import com.backmin.domains.review.domain.Review;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store")
public class Store extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "store_id", nullable = false)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "min_order_price")
    private int minOrderPrice;

    @Column(name = "min_delivery_time")
    private int minDeliveryTime;

    @Column(name = "max_delivery_time")
    private int maxDeliveryTime;

    @Column(name = "store_intro", length = 3000)
    private String storeIntro;

    @Column(name = "is_service")
    private boolean isService;

    @Column(name = "is_package")
    private boolean isPackage;

    @Column(name = "delivery_tip")
    @Min(0) /** todo: 삭제할 것*/
    private int deliveryTip;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Store(
            String name,
            String phoneNumber,
            int minOrderPrice,
            int minDeliveryTime,
            int maxDeliveryTime,
            String storeIntro,
            boolean isService,
            boolean isPackage,
            int deliveryTip,
            Category category,
            Member member
    ) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.minOrderPrice = minOrderPrice;
        this.minDeliveryTime = minDeliveryTime;
        this.maxDeliveryTime = maxDeliveryTime;
        this.storeIntro = storeIntro;
        this.isService = isService;
        this.isPackage = isPackage;
        this.deliveryTip = deliveryTip;
        this.category = category;
        this.menus = new ArrayList<>();
        this.member = member;
    }

    static public Store of(
            String name,
            String phoneNumber,
            String storeIntro,
            int minOrderPrice,
            int minDeliveryTime,
            int maxDeliveryTime,
            int deliveryTip,
            boolean isService,
            boolean isPackage,
            Category category
    ) {
        Store store = Store.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .minOrderPrice(minOrderPrice)
                .minDeliveryTime(minDeliveryTime)
                .maxDeliveryTime(maxDeliveryTime)
                .storeIntro(storeIntro)
                .isService(isService)
                .isPackage(isPackage)
                .deliveryTip(deliveryTip)
                .category(category)
                .build();

        return store;
    }

    static public Store of(
            String name,
            String phoneNumber,
            String storeIntro,
            int minOrderPrice,
            int minDeliveryTime,
            int maxDeliveryTime,
            int deliveryTip,
            boolean isService,
            boolean isPackage,
            Category category,
            Member member
    ) {
        Store store = Store.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .minOrderPrice(minOrderPrice)
                .minDeliveryTime(minDeliveryTime)
                .maxDeliveryTime(maxDeliveryTime)
                .storeIntro(storeIntro)
                .isService(isService)
                .isPackage(isPackage)
                .deliveryTip(deliveryTip)
                .category(category)
                .member(member)
                .build();
        return store;
    }

    static public Store of(
            String name,
            String phoneNumber,
            String storeIntro,
            int minOrderPrice,
            int minDeliveryTime,
            int maxDeliveryTime,
            int deliveryTip,
            boolean isService,
            boolean isPackage,
            Category category,
            List<Menu> menus
    ) {
        Store store = Store.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .minOrderPrice(minOrderPrice)
                .minDeliveryTime(minDeliveryTime)
                .maxDeliveryTime(maxDeliveryTime)
                .storeIntro(storeIntro)
                .isService(isService)
                .isPackage(isPackage)
                .deliveryTip(deliveryTip)
                .category(category)
                .build();

        menus.stream().forEach(menu -> store.addMenu(menu));

        return store;
    }

    public void addMenu(Menu menu) {
        menu.changeStore(this);
    }

    public void addReview(Review review) {
        review.changeStore(this);
    }

}
