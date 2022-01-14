package org.zchzh.file.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2021/5/4
 */

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class BaseFile implements Serializable {

    private static final long serialVersionUID = 3729393554289567140L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private Long folderId;

    @Transient
    private Folder folder;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * jpa version 乐观锁，解决并发更新的问题
     */
    @Version
    private Integer version;


    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    /**
     * 在实体保存到数据库之前执行的操作
     */
    @PrePersist
    public void prePersist(){
        this.createTime = this.updateTime = new Date();
    }

    /**
     * 在实体更新到数据库之前执行的操作
     */
    @PreUpdate
    public void preUpdate(){
        this.updateTime = new Date();
    }

    /**
     * 在实体从数据库删除之前执行的操作
     */
    @PreRemove
    public void preRemove(){
        this.updateTime = new Date();
    }

    public boolean isNull() {
        return Objects.isNull(id);
    }

    public static BaseFile getNull() {
        return new NullFile();
    }

    static class NullFile extends BaseFile {

        private static final long serialVersionUID = -2911531056526439715L;

        @Override
        public void setId(Long id) {
        }

        @Override
        public void setFileName(String fileName) {
        }

        @Override
        public void setFolderId(Long folderId) {
        }

        @Override
        public void setCreateTime(Date createTime) {
        }

        @Override
        public void setVersion(Integer version) {
        }

        @Override
        public void setUpdateTime(Date updateTime) {
        }
    }
}
