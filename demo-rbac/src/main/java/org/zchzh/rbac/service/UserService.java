package org.zchzh.rbac.service;

import org.zchzh.rbac.model.dto.LoginDTO;
import org.zchzh.rbac.model.dto.UserDTO;
import org.zchzh.rbac.model.entity.MyUser;
import org.zchzh.rbac.model.request.LoginReq;
import org.zchzh.rbac.model.request.RegisterReq;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zengchzh
 * @date 2021/7/30
 */
public interface UserService extends BaseCrudService<MyUser, Long> {

    /**
     * 登录
     * @param req 请求参数
     * @param request
     * @return 返回登录用户信息
     */
    LoginDTO login(LoginReq req, HttpServletRequest request);

    /**
     * 注册
     * @param req 注册参数
     * @return 返回注册的信息
     */
    UserDTO register(RegisterReq req);

}
