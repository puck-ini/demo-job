package org.zchzh.rbac.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.zchzh.rbac.filter.LoginFilterChain;
import org.zchzh.rbac.model.convert.UserConvert;
import org.zchzh.rbac.model.dto.LoginDTO;
import org.zchzh.rbac.model.dto.UserDTO;
import org.zchzh.rbac.model.entity.MyUser;
import org.zchzh.rbac.model.context.LoginContext;
import org.zchzh.rbac.model.request.LoginReq;
import org.zchzh.rbac.model.request.RegisterReq;
import org.zchzh.rbac.repository.UserRepo;
import org.zchzh.rbac.service.CacheService;
import org.zchzh.rbac.service.UserService;
import org.zchzh.rbac.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @author zengchzh
 * @date 2021/7/30
 */

@Service
public class UserServiceImpl extends AbstractCrudService<MyUser, Long> implements UserService {

    private final UserRepo userRepo;

    private final CacheService cacheService;

    /**
     * 登录名限制次数
     */
    private static final int LOGIN_NAME_FAIL_COUNT = 20;

    /**
     * ip 限制次数
     */
    private static final int IP_FAIL_COUNT = 10;

    protected UserServiceImpl(UserRepo repo, CacheService cacheService) {
        super(repo);
        this.userRepo = repo;
        this.cacheService = cacheService;
    }


    @Autowired
    private LoginFilterChain filterChain;

    @Autowired
    private ApplicationContext context;

    @Override
    public LoginDTO login(LoginReq req, HttpServletRequest request) {
        filterChain.check(new LoginContext(req, request.getRemoteAddr(), context));
        MyUser user = userRepo.findByUsername(req.getUsername());
        String token = JwtUtil.getToken(new HashMap<>(), user.getUsername());
        LoginDTO dto = UserConvert.INSTANCE.toLoginDto(user);;
        dto.setToken(token);
        return dto;
    }

    @Override
    public UserDTO register(RegisterReq req) {
        MyUser user = UserConvert.INSTANCE.toEntity(req);
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        return UserConvert.INSTANCE.toDto(userRepo.saveAndFlush(user));
    }



}
