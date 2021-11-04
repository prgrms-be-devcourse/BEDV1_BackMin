package com.backmin.domains.order.dto.request;

import com.backmin.domains.menu.dto.request.MenuReadParam;
import com.backmin.domains.order.domain.Payment;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderParam {

    @NotNull
    private Long memberId;

    @NotBlank
    private String password;

    @NotBlank
    private String address;

    private String requirement;

    @NotNull
    private Long storeId;

    @NotEmpty
    private List<MenuReadParam> menuReadParams;

    @NotNull
    private Payment payment;

}
