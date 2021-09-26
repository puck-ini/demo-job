package org.zchzh.rbac.model.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.zchzh.rbac.model.dto.RoleDTO;
import org.zchzh.rbac.model.entity.Role;
import org.zchzh.rbac.model.request.CreateRoleReq;
import org.zchzh.rbac.model.request.UpdateRoleReq;

/**
 * @author zengchzh
 * @date 2021/9/13
 */

@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    RoleDTO toDto(Role role);


    Role toEntity(CreateRoleReq req);

    Role toEntity(UpdateRoleReq req);
}
