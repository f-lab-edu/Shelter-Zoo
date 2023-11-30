package com.noint.shelterzoo.domain.abandoned.dto.req;

import lombok.Data;

@Data
public class AdoptUpdateRequestDTO {
    private Long petSeq;
    private String state;
}
