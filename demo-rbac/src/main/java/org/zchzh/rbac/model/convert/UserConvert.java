package org.zchzh.rbac.model.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.zchzh.rbac.model.dto.UserDTO;
import org.zchzh.rbac.model.entity.MyUser;
import org.zchzh.rbac.model.request.LoginReq;
import org.zchzh.rbac.model.request.RegisterReq;

/**
 * @author zengchzh
 * @date 2021/9/7
 */

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    @Mapping(source = "userDetail.age", target = "age")
    @Mapping(source = "userDetail.phone", target = "phone")
    @Mapping(source = "userDetail.mail", target = "mail")
    UserDTO toDto(MyUser user);


    MyUser toEntity(LoginReq req);

    MyUser toEntity(RegisterReq req);


}
