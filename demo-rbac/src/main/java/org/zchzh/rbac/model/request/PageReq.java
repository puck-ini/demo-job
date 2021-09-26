package org.zchzh.rbac.model.request;

import lombok.Data;

/**
 * @author zengchzh
 * @date 2021/9/13
 */

@Data
public class PageReq {

    private Integer pageNum;

    private Integer pageSize;
}
