package com.we_are_team.school_background_service_system.interceptor;

import com.we_are_team.school_background_service_system.context.BaseContext;
import com.we_are_team.school_background_service_system.properties.JwtProperties;
import com.we_are_team.school_background_service_system.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JwtTokenUserInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    private String jwtToken;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());

        if (token == null) {
            //当前用户未登录
            log.info("用户未登录");
            response.setStatus(401);
            return false;
        }else {
            log.info("用户已登录，token:{}", token);
            jwtToken = token.replace("Bearer ", "");
        }



        //2、校验令牌
        try {
            log.info("jwt校验:{}", jwtToken);
            Claims claims = JwtUtil.parseJWT(jwtToken,jwtProperties.getUserSecretkey());
            log.info("jwt校验成功:{}", claims);
            Integer userId = (Integer) claims.get("id");
            BaseContext.setCurrentId(userId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            log.error("jwt校验失败:{}", ex.getMessage());
            //4、不通过，响应401状态码
            response.setStatus(401);
            return false;
        }
    }
}
