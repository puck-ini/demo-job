package org.zchzh.rbac.filter;

import cn.hutool.crypto.digest.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.zchzh.rbac.exception.CommonException;
import org.zchzh.rbac.model.convert.UserConvert;
import org.zchzh.rbac.model.entity.MyUser;
import org.zchzh.rbac.model.context.LoginContext;
import org.zchzh.rbac.model.request.LoginReq;
import org.zchzh.rbac.repository.UserRepo;

import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
@Slf4j
public class CheckPwFilter extends BaseFilter<LoginContext> {

    private UserRepo userRepo;

    public CheckPwFilter(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void execute(LoginContext context, BaseFilter<LoginContext> filter) {
        log.info(CheckPwFilter.class.getName());
        LoginReq req = context.getReq();
        MyUser loginUser = UserConvert.INSTANCE.toEntity(req);
        MyUser user = userRepo.findByUsername(req.getUsername());
        if (!checkPw(loginUser, user)) {
            onFail(context);
            throw new CommonException("用户名或密码错误");
        }
        super.execute(context, filter);
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
}
