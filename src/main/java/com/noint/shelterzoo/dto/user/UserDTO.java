package com.noint.shelterzoo.dto.user;

import com.noint.shelterzoo.vo.user.UserVO;
import lombok.Data;

import java.math.BigDecimal;

public class UserDTO {
    @Data
    public static class Signup {
        private String email;
        private String nickname;
        private String password;
    }

    @Data
    public static class Login{
        private String email;
        private String password;
    }

    @Data
    public static class MyInfo{
        private Long seq;
        private String email;
        private String nickname;
        private BigDecimal money;
        private String state;
        private String createdAt;

        public static MyInfo create(UserVO.MyInfo vo){
            MyInfo dto = new MyInfo();
            dto.setSeq(vo.getSeq());
            dto.setEmail(vo.getEmail());
            dto.setNickname(vo.getNickname());
            dto.setMoney(vo.getMoney());
            dto.setState(vo.getState());
            dto.setCreatedAt(vo.getCreatedAt());

            return dto;
        }
    }
}
