package com.backmin.domains.menu.dto;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuReadRequest {

    private Long id;

    @Min(0)
    @Max(10)
    private int quantity;

    private List<MenuOptionReadRequest> menuOptionReadRequests;

}
