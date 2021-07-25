package com.Element.Music.Interceptor;

import com.Element.Music.Cache.SessionCache;
import com.Element.Music.Util.EncryptUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestId = request.getParameter("requestId");
        String pwd = EncryptUtil.encrypt(requestId);
        String rwd = request.getParameter("rwd");
        if (rwd == null || rwd == "") {
            throw new Exception("illegal request");
        }
        return pwd == rwd;
    }
}