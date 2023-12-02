package com.noint.shelterzoo.domain.abandoned.dto.req;

import com.github.pagehelper.PageParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AbandonedListRequestDTO extends PageParam {
    private String location;
    private String kind;
    private String gender;
    private String neuter;
}