package org.zchzh.rbac.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author zengchzh
 * @date 2021/7/30
 */

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Integer age;

    private String phone;

    private String mail;
}
