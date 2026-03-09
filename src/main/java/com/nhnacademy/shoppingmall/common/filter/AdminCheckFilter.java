package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebFilter(filterName =  "adminCheckFilter", urlPatterns = "/admin/*")
public class AdminCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#11 /admin/ 하위 요청은 관리자 권한의 사용자만 접근할 수 있습니다. ROLE_USER가 접근하면 403 Forbidden 에러처리
        HttpSession session = req.getSession(false);
        User user = (Objects.nonNull(session)) ? (User) session.getAttribute("user") : null;
        if (Objects.isNull(user)) {
            res.sendRedirect("/login.do");
            return;
        }
        if (user.getUserAuth() != User.Auth.ROLE_ADMIN){
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "어드민만 접근 가능한 페이지");
            return;
        }
        chain.doFilter(req, res);
    }
}
