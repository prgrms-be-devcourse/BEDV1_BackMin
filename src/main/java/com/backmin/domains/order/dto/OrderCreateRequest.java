package com.backmin.domains.order.dto;

import com.backmin.domains.menu.dto.MenuReadRequest;
import com.backmin.domains.order.domain.Payment;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderCreateRequest {

    private Long memberId;

    private String password;

    private String address;

    private String requirement;

    private Long storeId;

    private List<MenuReadRequest> menuReadRequests;

    private Payment payment;

}
