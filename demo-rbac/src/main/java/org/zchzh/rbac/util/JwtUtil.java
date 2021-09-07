package org.zchzh.rbac.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.Base64Utils;

import java.security.Key;
import java.util.*;

/**
 * @author zengchzh
 * @date 2021/9/7
 */
public class JwtUtil {

    private static final String SECRET = "38012938120JKLJKLJKUEIjklwjeklqjei89080dsjlkdjsakj39120aaa9";

    public static String getToken(Map<String, Object> claims, String subject) {
        Date now = Calendar.getInstance().getTime();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 1000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET)), SignatureAlgorithm.HS256)
                .compact();
    }


    public static Claims parser(String token) {
        return Jwts.parserBuilder().setSigningKey(SECRET).build().parseClaimsJws(token).getBody();
    }

    public static Object get(String token, String key) {
        return parser(token).get(key);
    }

//
//    public static void main(String[] args) {
//        Map<String, Object> map = new HashMap<>();
//        map.put("test","test123");
//        String token = JwtUtil.getToken(map, "te1");
//        System.out.println(JwtUtil.parser(token).getSubject());
//        System.out.println(JwtUtil.parser(token).get("test"));
//    }
}
