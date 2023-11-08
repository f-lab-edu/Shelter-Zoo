package com.noint.shelterzoo.domain.user.dto.req;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String email;
    private String nickname;
    private String password;
}
