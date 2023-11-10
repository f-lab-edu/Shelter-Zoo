package com.noint.shelterzoo.user.dto;

import lombok.Data;

public class UserDTO {
    @Data
    public static class Signup {
        private String email;
        private String nickname;
        private String password;
    }
}
