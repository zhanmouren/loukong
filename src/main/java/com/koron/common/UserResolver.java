package com.koron.common;

import com.koron.common.stub.Port;
import com.koron.inwlms.bean.VO.sysManager.UserVO;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @auother:zhongweibin
 * @date:2020-06-12
 * @description:
 */
public class UserResolver implements HandlerMethodArgumentResolver {
    public UserResolver() {
    }
    public boolean supportsParameter(MethodParameter parameter) {
        StaffAttribute attr = (StaffAttribute)parameter.getParameterAnnotation(StaffAttribute.class);
        return parameter.getParameterType().equals(UserVO.class) && attr != null;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        StaffAttribute attr = (StaffAttribute)parameter.getParameterAnnotation(StaffAttribute.class);
        HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest(HttpServletRequest.class);
        Object o = request.getSession().getAttribute(attr.value());
        if (o != null) {
            return request.getSession().getAttribute(attr.value());
        } else {
            String ticket = request.getParameter("ticket");
            if (ticket == null) {
                Cookie[] cookies = request.getCookies();
                if (cookies == null) {
                    return null;
                }

                Cookie[] var10 = cookies;
                int var11 = cookies.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    Cookie cookie = var10[var12];
                    if (cookie.getName().equals("ticket")) {
                        ticket = cookie.getValue();
                        break;
                    }
                }
            }

            return ticket != null && !ticket.isEmpty() ? (new Port()).getStaff(ticket).getData() : null;
        }
    }
}
