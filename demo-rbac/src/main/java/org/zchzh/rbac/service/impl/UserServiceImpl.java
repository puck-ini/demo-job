package org.zchzh.rbac.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.zchzh.rbac.entity.MyUser;
import org.zchzh.rbac.repository.UserRepo;
import org.zchzh.rbac.service.UserService;

/**
 * @author zengchzh
 * @date 2021/7/30
 */

@Service
public class UserServiceImpl extends AbstractCrudService<MyUser, Long> implements UserService {

    private final UserRepo userRepo;

    protected UserServiceImpl(UserRepo repo) {
        super(repo);
        this.userRepo = repo;
    }
}
