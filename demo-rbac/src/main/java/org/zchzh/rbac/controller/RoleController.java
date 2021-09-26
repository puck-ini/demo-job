package org.zchzh.rbac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.zchzh.rbac.model.convert.RoleConvert;
import org.zchzh.rbac.model.dto.PageDTO;
import org.zchzh.rbac.model.dto.RoleDTO;
import org.zchzh.rbac.model.entity.Role;
import org.zchzh.rbac.model.request.CreateRoleReq;
import org.zchzh.rbac.model.request.PageReq;
import org.zchzh.rbac.model.request.UpdateRoleReq;
import org.zchzh.rbac.service.RoleService;

/**
 * @author zengchzh
 * @date 2021/9/13
 */

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public RoleDTO create(@RequestBody CreateRoleReq req) {
        return RoleConvert.INSTANCE.toDto(roleService.create(RoleConvert.INSTANCE.toEntity(req)));
    }

    @GetMapping()
    public Role get(@RequestParam("roleId") Long roleId) {
        return roleService.get(roleId).orElse(new Role());
    }


    @GetMapping("/list")
    public PageDTO<Role> list(PageReq req) {
        return new PageDTO<>(roleService.list(PageRequest.of(req.getPageNum(), req.getPageSize())));
    }

    @PutMapping("/update")
    public RoleDTO update(UpdateRoleReq req) {
        Role role = roleService.get(req.getRoleId()).orElse(new Role());
        role.setName(req.getName());
        role.setDescription(req.getDescription());
        return RoleConvert.INSTANCE.toDto(roleService.update(role));
    }

    @DeleteMapping("/remove")
    public RoleDTO remove(Long roleId) {
        return RoleConvert.INSTANCE.toDto(roleService.remove(roleId));
    }
}
