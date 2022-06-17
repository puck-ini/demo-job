package org.zchzh.file.model;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/6/17
 */

@Data
public class Range {

    private Long begin;

    private Long end;

    private Long len;


    public static Range getRange(HttpServletRequest request) {
        String rangeStr = request.getHeader("Range");
        if (StringUtils.isEmpty(rangeStr)) {
            return new Range();
        }
        checkRange(rangeStr);
        String[] rangeArr = rangeStr.split("bytes=|-");
        Range range = new Range();
        int len = rangeArr.length;
        if (len < 1) {
            throw new IllegalArgumentException("range不符合");
        } else if (len == 2){
            range.setBegin(Long.parseLong(rangeArr[1]));
        } else if (len == 3) {
            range.setBegin(Long.parseLong(rangeArr[1]));
            range.setEnd(Long.parseLong(rangeArr[2]));
        } else {
            throw new IllegalArgumentException("range不符合");
        }
        return range;
    }


    private static void checkRange(String rangeStr) {
        if (!rangeStr.contains("bytes")) {
            throw new IllegalArgumentException("只支持字节单位");
        }
    }


    public Long getLen() {
        if (Objects.isNull(this.len)) {
            if (Objects.isNull(this.end)) {
                return null;
            }
            this.len = this.end - this.begin + 1;
        }
        return this.len;
    }
}
