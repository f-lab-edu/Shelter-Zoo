package com.noint.shelterzoo.domain.abandoned.dto.req;

import lombok.Data;

@Data
public class AbandonedListRequestDTO {
    private String location;
    private String kind;
    private String gender;
    private String neuter;
    private Integer pageNum;
    private Integer pageSize;
}