package com.noint.shelterzoo.domain.pin.dto.req;

import lombok.Data;

@Data
public class PinListRequestDTO {
    private Integer pageNum;
    private Integer pageSize;
}