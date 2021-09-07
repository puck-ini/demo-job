package org.zchzh.rbac.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import org.springframework.stereotype.Service;
import org.zchzh.rbac.exception.CommonException;
import org.zchzh.rbac.model.convert.UserConvert;
import org.zchzh.rbac.model.dto.UserDTO;
import org.zchzh.rbac.model.entity.MyUser;
import org.zchzh.rbac.model.request.LoginReq;
import org.zchzh.rbac.model.request.RegisterReq;
import org.zchzh.rbac.repository.UserRepo;
import org.zchzh.rbac.service.CacheService;
import org.zchzh.rbac.service.UserService;
import org.zchzh.rbac.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

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

    @Override
    public UserDTO login(LoginReq req, HttpServletRequest request) {
        MyUser loginUser = UserConvert.INSTANCE.toEntity(req);
        MyUser user = userRepo.findByUsername(req.getUsername());
        String username = req.getUsername();
        String ip = request.getRemoteAddr();
        checkUsernameLimiter(username);
        checkIpLimiter(username, ip);
        if (!checkPw(loginUser, user)) {
            incrLimitCount(username, ip);
            throw new CommonException("用户名或密码错误");
        }
        String token = JwtUtil.getToken(null, user.getUsername());
        return UserConvert.INSTANCE.toDto(user);
    }

    private boolean checkPw(MyUser loginUser, MyUser storeUser) {
        if (Objects.isNull(loginUser) || Objects.isNull(storeUser)) {
            throw new CommonException("用户不存在");
        }
        try {
            return BCrypt.checkpw(loginUser.getPassword(), storeUser.getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    private void checkUsernameLimiter(String username) {
        Integer count = (Integer) Optional.ofNullable(cacheService.get(username)).orElseGet(new Supplier<Integer>() {
            @Override
            public Integer get() {
                cacheService.set(username, 0);
                return 0;
            }
        });
        if (count > LOGIN_NAME_FAIL_COUNT) {
            throw new CommonException("登录限制");
        }
    }

    private void checkIpLimiter(String username, String ip) {
        String key = username + ":" + ip;
        Integer count = (Integer) Optional.ofNullable(cacheService.get(key)).orElseGet(new Supplier<Integer>() {
            @Override
            public Integer get() {
                cacheService.set(key, 0);
                return 0;
            }
        });
        if (count > IP_FAIL_COUNT) {
            throw new CommonException("ip登录限制");
        }
    }

    private void incrLimitCount(String username, String ip) {
        cacheService.incr(username);
        cacheService.incr(username + ":" + ip);
    }

    @Override
    public UserDTO register(RegisterReq req) {
        MyUser user = UserConvert.INSTANCE.toEntity(req);
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        return UserConvert.INSTANCE.toDto(userRepo.saveAndFlush(user));
    }



}
