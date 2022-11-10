package com.oscar.springbootstudy.tools.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Jwt即java web token，是比较成熟的token方案，
 * JwtUtil里面有生成token的方法、校验token是否有效是否过期的方法、
 * 以及通过token获取解析值的方法，这里我们可以设置token的有效时间。
 *
 */
@Component
public class JwtUtil {

    // jwt认证国企时间 EXPIRE_TIME 分钟
    private static final long EXPIRE_TIME = 600 * 1000;

    /**
     * 校验token是否正确
     *
     */
    public static boolean verify(String token, String username, String password) {
        try {
            // 根据密码生成JWT校验器
            Algorithm algorithm = Algorithm.HMAC256(password);
            JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
            // 校验token
            DecodedJWT jwt = verifier.verify(token);
            System.out.println("登录验证成功！");
            return true;
        } catch (Exception ex) {
            System.out.println("JwtUtil登录验证失败");
            return false;
        }
    }

    /**
     * 获得token中的信息，无需password揭秘也能获得
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 生成token签名，EXPIRE_TIME后过期
     */
    public static String sign(String username, String password) {
        long nowTime = System.currentTimeMillis();
        Date date = new Date(nowTime + EXPIRE_TIME);

        Algorithm algorithm = Algorithm.HMAC256(password);
        // 附带username信息
        return JWT.create()
                .withClaim("username", username)
                .withClaim("currentTimeMillis", String.valueOf(nowTime))
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static String getClaim(String token, String claim){
        try{
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
    * 使用md5加密
    */
    public static String md5Encode(String source, String salt) {
        return new Md5Hash(source + salt).toHex();
    }

    public static void main(String[] args) {
        System.out.println(md5Encode("1qazxsw2", "oscar"));
    }
}
