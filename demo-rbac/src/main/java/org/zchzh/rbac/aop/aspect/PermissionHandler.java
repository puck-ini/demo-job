package org.zchzh.rbac.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.zchzh.rbac.exception.CommonException;
import org.zchzh.rbac.service.PermissionService;
import org.zchzh.rbac.type.PermissionType;
import org.zchzh.rbac.type.Url;
import org.zchzh.rbac.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/9/13
 */

@Slf4j
@Component
public class PermissionHandler extends BaseMethodAdviceHandler<Object> {

    @Autowired
    private PermissionService permissionService;

    @Value("${rbac.permission.enable}")
    public boolean enable;

    private static List<String> whiteList = new ArrayList<>();

    static {
        whiteList.add("/user/login");
        whiteList.add("/user/register");
    }

    @Override
    public boolean onBefore(ProceedingJoinPoint point) {
        if (!enable) {
            return true;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();
        if (checkWhiteList(url)) {
            return true;
        }
        String method = request.getMethod();
        String token = request.getHeader("token");
        if (Objects.isNull(token)) {
            return false;
        }
        Long userId = (Long) JwtUtil.get(token, "userId");
        return permissionService.hasPermission(userId, PermissionType.BACK_END, new Url(url, HttpMethod.resolve(method)));
    }

    private boolean checkWhiteList(String url) {
        PathMatcher matcher = new AntPathMatcher();
        for (String whiteUrl : whiteList) {
            if (matcher.match(whiteUrl, url)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object getOnForbid(ProceedingJoinPoint point) {
        throw new CommonException("无权限访问");
    }
}
