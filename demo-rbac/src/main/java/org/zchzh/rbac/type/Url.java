package org.zchzh.rbac.type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

/**
 * @author zengchzh
 * @date 2021/9/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Url implements Serializable {

    /**
     * 权限类型为前端页面时表示前端url，权限类型为后端接口时表示后端接口url
     */
    private String url;
    /**
     * 请求方式
     */
    @Enumerated(EnumType.ORDINAL)
    private HttpMethod method;
}
