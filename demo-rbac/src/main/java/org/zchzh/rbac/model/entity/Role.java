package org.zchzh.rbac.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zengchzh
 * @date 2021/7/30
 */

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity{

    private static final long serialVersionUID = -758230998840266577L;

    private String name;

    private String description;

    @JsonIgnore
    @Builder.Default
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL, targetEntity = MyUser.class)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),
            inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<MyUser> users = new ArrayList<>();

    @Builder.Default
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL, targetEntity = Permission.class, fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT),
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")},
            inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private List<Permission> permissions = new ArrayList<>();
}
