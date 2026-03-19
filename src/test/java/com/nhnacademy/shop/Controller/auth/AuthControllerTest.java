//package com.nhnacademy.shop.Controller.auth;
//
//import com.nhnacademy.shoppingmall.controller.auth.LoginController;
//import com.nhnacademy.shoppingmall.controller.auth.LoginPostController;
//import com.nhnacademy.shoppingmall.controller.auth.LogoutController;
//import com.nhnacademy.shoppingmall.controller.auth.SignupController;
//import com.nhnacademy.shoppingmall.user.domain.User;
//import com.nhnacademy.shoppingmall.user.service.UserService;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AuthControllerTest {
//
//    @Mock private HttpServletRequest request;
//    @Mock private HttpServletResponse response;
//    @Mock private HttpSession session;
//    @Mock private ServletContext servletContext;
//    @Mock private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        lenient().when(request.getServletContext()).thenReturn(servletContext);
//        lenient().when(servletContext.getAttribute("userService")).thenReturn(userService);
//    }
//
//    @Test
//    @DisplayName("로그인 폼 요청 - 세션 있으면 인덱스로 리다이렉트")
//    void loginController_AlreadyLoggedIn_Redirect() {
//        LoginController controller = new LoginController();
//        when(request.getSession(false)).thenReturn(session);
//        when(session.getAttribute("user")).thenReturn(mock(User.class));
//
//        String viewName = controller.execute(request, response);
//
//        assertEquals("redirect:/index.do", viewName);
//    }
//
//    @Test
//    @DisplayName("로그인 실행 - 성공 시 세션 생성 및 리다이렉트")
//    void loginPostController_Success() {
//        LoginPostController controller = new LoginPostController();
//        ReflectionTestUtils.setField(controller, "userService", userService);
//        User mockUser = new User("admin", "관리자", "1234", "1990", User.Auth.ROLE_USER, 1000, LocalDateTime.now(), null);
//        when(request.getParameter("user_id")).thenReturn("admin");
//        when(request.getParameter("user_password")).thenReturn("1234");
//        when(userService.doLogin("admin", "1234")).thenReturn(mockUser);
//        when(request.getSession(true)).thenReturn(session);
//        String viewName = controller.execute(request, response);
//        assertEquals("redirect:/index.do", viewName);
//        verify(session).setAttribute("user", mockUser);
//        verify(session).setMaxInactiveInterval(3600);
//    }
//
//    @Test
//    @DisplayName("로그인 실행 - 실패 시 에러 메시지 세팅 및 폼 반환")
//    void loginPostController_Fail() {
//        LoginPostController controller = new LoginPostController();
//        ReflectionTestUtils.setField(controller, "userService", userService);
//        when(request.getParameter("user_id")).thenReturn("wrong");
//        when(userService.doLogin(anyString(), anyString())).thenThrow(new RuntimeException("Fail"));
//        String viewName = controller.execute(request, response);
//        assertEquals("shop/login/login_form", viewName);
//        verify(request).setAttribute(eq("error"), anyString());
//    }
//
//    @Test
//    @DisplayName("로그아웃 - 세션 무효화 및 인덱스 리다이렉트")
//    void logoutController_Success() {
//        LogoutController controller = new LogoutController();
//        when(request.getSession(false)).thenReturn(session);
//        String viewName = controller.execute(request, response);
//        assertEquals("redirect:/index.do", viewName);
//        verify(session).invalidate();
//    }
//
//    @Test
//    @DisplayName("회원가입 폼 요청 - 기존 세션 파괴 확인")
//    void signupController_InvalidateSession() {
//        SignupController controller = new SignupController();
//        when(request.getSession(false)).thenReturn(session);
//        String viewName = controller.execute(request, response);
//        assertEquals("shop/signup/signup_form", viewName);
//        verify(session).invalidate();
//    }
//}
