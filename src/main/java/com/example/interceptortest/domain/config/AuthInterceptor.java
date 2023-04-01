package com.example.interceptortest.domain.config;

import com.example.interceptortest.domain.role.Role;
import com.example.interceptortest.domain.user.User;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Interceptor 사용을 위해서는 Interceptor와 Interceptor를 등록하기 위한 Config가 필요하다.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * handler 종류 확인 -> handlerMethod 타입인지? 체크
         * handlerMethod 가 아니라면 진행
         */
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 형 변환
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 커스텀 어노테이션 받아오기
        MyAnnotation myAnnotation = handlerMethod.getMethodAnnotation(MyAnnotation.class);
        // method 에 @MyAnnotation이 없는 경우 == 인증이 필요 없는 요청일 경우
        if (myAnnotation == null) {
            return true;
        }
        // @MyAnnotation이 있는 경우이미, session 여부 체크
        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect("/index/error");
            return false;
        }

        // session 이 존재했으니, 이제 유효한 유저인가 체크
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("/index/error");
            return false;
        }

        // admin 일 경우?
        String role = myAnnotation.role().toString();
        if (role != null) {
            if ("ADMIN".equals(role)) {
                if (user.getRole() != Role.ADMIN) {
                    response.sendRedirect("/index/error");
                    return false;
                }
            }
        }
        // 접근을 허가
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
