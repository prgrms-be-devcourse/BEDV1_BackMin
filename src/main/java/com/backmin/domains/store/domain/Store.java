package com.backmin.domains.store.domain;

import com.backmin.domains.menu.domain.MenuCategory;
import com.backmin.domains.review.domain.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Min;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store")
public class Store {

    @Id
    @GeneratedValue
    @Column(name = "store_id", nullable = false)
    private Long id;

    @Column(name = "store_name", length = 50, nullable = false)
    private String name;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "min_oder_price")
    @Min(0)
    private int minOrderPrice;

    @Column(name = "min_deliv_time")
    @Min(0)
    private int minDeliveryTime;

    @Column(name = "max_deliv_time")
    @Min(0)
    private int maxDeliveryTime;

    @Column(name = "store_intro", length = 3000, nullable = false)
    private String storeIntro;

    @Column(name = "is_service")
    private boolean isService;

    @Column(name = "main_intro", length = 3000, nullable = false)
    private String mainIntro;

    @Column(name = "is_package")
    private boolean isPackage;

    @Column(name = "deliv_tip")
    @Min(0)
    private int deliveryTip;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MenuCategory> menuCategories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Store(Long id,
                 String name,
                 String phoneNumber,
                 int minOrderPrice,
                 int minDeliveryTime,
                 int maxDeliveryTime,
                 String storeIntro,
                 boolean isService,
                 String mainIntro,
                 boolean isPackage,
                 int deliveryTip,
                 Category category
    ) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.minOrderPrice = minOrderPrice;
        this.minDeliveryTime = minDeliveryTime;
        this.maxDeliveryTime = maxDeliveryTime;
        this.storeIntro = storeIntro;
        this.isService = isService;
        this.mainIntro = mainIntro;
        this.isPackage = isPackage;
        this.deliveryTip = deliveryTip;
        this.category = category;
    }

    public void addMenuCategory(MenuCategory menuCategory) {
        menuCategory.changeStore(this);
    }

    public void addReview(Review review) {
        review.changeStore(this);
    }

}
