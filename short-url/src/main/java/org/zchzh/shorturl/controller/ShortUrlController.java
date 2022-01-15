package org.zchzh.shorturl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zchzh.shorturl.service.ShortUrlService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zchzh
 * @date 2022/1/15
 */

@RestController
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;

    @PostMapping("/generate")
    public String generate(@RequestBody String longUrl) {
        return shortUrlService.getShortUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public void redirectLongUrl(@PathVariable("shortUrl") String shortUrl,
                                HttpServletResponse response) throws IOException {
        // 302 重定向
        response.sendRedirect(shortUrlService.getLongUrl(shortUrl));
    }
}
