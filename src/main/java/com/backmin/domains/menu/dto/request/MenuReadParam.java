package com.backmin.domains.menu.dto.request;

import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuReadParam {

    private Long id;

    @Min(0)
    @Max(10)
    private int quantity;

    private List<Long> menuOptionIds;

}
