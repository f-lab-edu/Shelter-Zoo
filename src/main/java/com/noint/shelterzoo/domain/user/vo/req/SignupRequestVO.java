package com.noint.shelterzoo.domain.user.vo.req;

import com.noint.shelterzoo.domain.user.dto.req.SignupRequestDTO;
import lombok.Data;

@Data
public class SignupRequestVO {
    private String email;
    private String nickname;
    private String password;

    public static SignupRequestVO create(SignupRequestDTO dto) {
        SignupRequestVO vo = new SignupRequestVO();
        vo.setEmail(dto.getEmail());
        vo.setPassword(dto.getPassword());
        vo.setNickname(dto.getNickname());

        return vo;
    }
}
