package com.backmin.domains.member.dto.response;

import com.backmin.domains.menu.dto.response.MenuReadResult;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberOrderPageResult {

    private Long orderId;

    private Long storeId;

    private String storeName;

    private List<MenuReadResult> menuReadRespons;

    private LocalDateTime OrderDateTime;

}
