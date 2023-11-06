package com.noint.shelterzoo.vo.user;

import com.noint.shelterzoo.dto.user.UserDTO;
import lombok.Data;

import java.math.BigDecimal;

public class UserVO {
    @Data
    public static class Signup {
        private String email;
        private String nickname;
        private String password;

        public static Signup create(UserDTO.Signup dto){
            Signup vo = new Signup();
            vo.setEmail(dto.getEmail());
            vo.setPassword(dto.getPassword());
            vo.setNickname(dto.getNickname());

            return vo;
        }
    }

    @Data
    public static class MyInfo{
        private Long seq;
        private String email;
        private String nickname;
        private BigDecimal money;
        private String state;
        private String createdAt;
    }
}
