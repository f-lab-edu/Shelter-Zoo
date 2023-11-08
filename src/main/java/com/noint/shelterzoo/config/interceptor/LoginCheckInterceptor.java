package com.noint.shelterzoo.config.interceptor;

import com.noint.shelterzoo.domain.user.enums.UserExceptionEnum;
import com.noint.shelterzoo.domain.user.exception.UserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
    private final HttpSession session;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object seq = session.getAttribute("seq");
        if (ObjectUtils.isEmpty(seq)) {
            log.warn("로그인 정보 없음.");
            throw new UserException(UserExceptionEnum.LOGIN_INFO_EMPTY);
        }
        return true;
    }
}
