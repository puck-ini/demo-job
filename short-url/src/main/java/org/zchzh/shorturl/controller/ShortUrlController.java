package org.zchzh.shorturl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zchzh.shorturl.service.UrlMapService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zchzh
 * @date 2022/1/15
 */

@RestController
public class ShortUrlController {

    @Autowired
    private UrlMapService urlMapService;

    @PostMapping("/generate")
    public String generate(@RequestBody String longUrl) {
        return urlMapService.getShortUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public void redirectLongUrl(@PathVariable("shortUrl") String shortUrl,
                                HttpServletResponse response) throws IOException {
        // 302 重定向
        response.sendRedirect(urlMapService.getLongUrl(shortUrl));

        // 301 重定向
//        response.setStatus(301);
//        response.setHeader("Location", urlMapService.getLongUrl(shortUrl));
    }

    @PostMapping("/custom")
    public String custom(@RequestParam String shortUrl,
                         @RequestParam String longUrl) {
        return urlMapService.custom(shortUrl, longUrl);
    }
}
