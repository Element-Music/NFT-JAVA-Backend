package com.Element.Music.interceptor;

import com.Element.Music.Cache.SessionCache;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessionId = request.getParameter("sessionId");
        if (sessionId == null || sessionId == "") {
            throw new Exception("老哥想啥呢，先登录好吧");
        }
        if (!SessionCache.ifExist(sessionId)) {

        }
        return true;
    }
}