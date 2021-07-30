package org.zchzh.rbac.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengchzh
 * @date 2021/7/30
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyUser extends BaseEntity{

    private String username;

    private String password;

    private String name;

    @ToString.Exclude
    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = Role.class)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Role> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserDetail userDetail;
}
