package org.zchzh.shorturl.util;

import cn.hutool.core.lang.hash.MurmurHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zchzh.shorturl.model.entity.SegmentIncrId;
import org.zchzh.shorturl.repo.SegmentIncrIdRepo;
import org.zchzh.shorturl.util.SpringContextUtils;

import java.util.Objects;

/**
 * @author zengchzh
 * @date 2022/1/20
 */

@Component
public class ShortUrlBuilder {

    private static final char[] CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static final int SIZE = CHARS.length;

    @Autowired
    private SegmentIncrIdRepo segmentIncrIdRepo;

    private SegmentIncrId segmentIncrId;


    public String hash(String longUrl) {
        return base62(Math.abs(MurmurHash.hash32(longUrl)));
    }

    public synchronized String incr(String longUrl) {
        if (Objects.isNull(segmentIncrId)) {
            segmentIncrId = getNextSegment();
        }
        Long id = segmentIncrId.nextId();
        if (Objects.isNull(id)) {
            segmentIncrId = getNextSegment();
            id = segmentIncrId.nextId();
        }
        return base62(id);
    }


    private SegmentIncrId getNextSegment() {
        return segmentIncrIdRepo.save(new SegmentIncrId());
    }

    private String base62(long num) {
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int i = (int) (num % SIZE);
            sb.append(CHARS[i]);
            num /= SIZE;
        }
        return sb.reverse().toString();
    }
}
