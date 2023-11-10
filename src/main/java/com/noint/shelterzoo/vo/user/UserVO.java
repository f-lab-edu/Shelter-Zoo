package com.noint.shelterzoo.vo.user;

import com.noint.shelterzoo.user.dto.UserDTO;
import lombok.Data;

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
}
