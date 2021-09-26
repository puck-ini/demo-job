package org.zchzh.rbac.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * @author zengchzh
 * @date 2021/6/8
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogEntity extends BaseEntity {

    private static final long serialVersionUID = 6008093151196967049L;
    /**
     * 线程id
     */
    private String threadId;
    /**
     * 线程名称
     */
    private String threadName;
    /**
     * 日志描述
     */
    private String description;
    /**
     * ip
     */
    private String ip;
    /**
     * url
     */
    private String url;
    /**
     * http方法 GET POST PUT DELETE PATCH
     */
    private String httpMethod;
    /**
     * 类方法
     */
    private String classMethod;
    /**
     * 请求参数
     */
    @Transient
    private String requestParams;
    /**
     * 返回参数
     */
    @Transient
    private String result;
    /**
     * 接口耗时
     */
    private Long timeCost;
    /**
     * 操作系统
     */
    private String os;
    /**
     * 浏览器
     */
    private String browser;
    /**
     * user-agent
     */
    private String userAgent;
}
