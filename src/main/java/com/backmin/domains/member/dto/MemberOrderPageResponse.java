package com.backmin.domains.member.dto;

import com.backmin.domains.menu.dto.MenuReadResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberOrderPageResponse {

    private Long orderId;

    private Long storeId;

    private String storeName;

    private List<MenuReadResponse> menuReadResponses;

    private LocalDateTime OrderDateTime;

}
