package com.example.common.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.common.exception.ServiceException;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.*;

/**
 * Token 工具类
 */
@Component
@Data
@NoArgsConstructor
public class TokenUtil {
    @Autowired
    private RedisTemplate redisTemplate = RedisUtil.redis;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${app.token.expire}")
    private int expire; //有效期,刷新token是它的6倍
    @Value("${app.token.prefix}")
    private String prefix; //缓存前缀
    @Value("${app.token.unique}")
    private boolean unique;  //是否单人登陆，不允许多人登陆同一个账号
    @Value("${app.token.autoEffect}")
    private boolean autoEffect; //是否自动生效
    @Value("${app.common.appSecret}")
    private String appSecret; //秘钥
    private Map<String,Object> redisData = new HashMap<>(); //存放到token数据

    public TokenUtil(int expire, String prefix, boolean unique, boolean autoEffect, String appSecret) {
        this.expire = expire;
        this.prefix = prefix;
        this.unique = unique;
        this.autoEffect = autoEffect;
        this.appSecret = appSecret;
    }

    /**
     * 获取token
     * @param id
     * @param uuid
     * @param model
     * @return
     */
    public HashMap<String, Object> getToken(int id, String uuid,String model) {
        //获取一个随机字符串
        String random = DigestUtils.md5DigestAsHex(
                String.valueOf(
                        UUID.randomUUID()
                ).getBytes()
        );
        //生成accessToken
        String accessToken = JWT
                .create()
                .withClaim("type", "token")
                .withClaim("model", model)
                .withClaim("random", random)
                .sign(Algorithm.HMAC256(this.appSecret));
        //生成refreshToken
        String refreshToken = JWT
                .create()
                .withClaim("accessToken", accessToken)
                .withClaim("type", "refreshToken")
                .withClaim("model", model)
                .withClaim("random", random)
                .sign(Algorithm.HMAC256(this.appSecret));
        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("token", accessToken);
        res.put("refreshToken", refreshToken);
        res.put("expire", (System.currentTimeMillis() / 1000) + this.expire);
        //是否配置自动生效
        if (this.autoEffect) {
            this.effectToken(id, uuid, res);
        }
        return res;
    }

    /**
     * 生效Token
     *
     * @param id
     * @param uuid
     * @param token
     */
    public void effectToken(int id, String uuid, HashMap<String, Object> token) {
        String accessTokenKey = this.prefix + "access_" + uuid;
        String refreshTokenKey = this.prefix + "refresh_" + uuid;
        HashSet<String> accessTokenList = redisUtil.isExpire(accessTokenKey) ? new HashSet<String>() : JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(accessTokenKey)), HashSet.class);
        HashSet<String> refreshTokenList = redisUtil.isExpire(refreshTokenKey) ? new HashSet<String>() : JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(refreshTokenKey)), HashSet.class);
        //判断是否只支持单点登录
        if (this.unique) {
            // 清空之前的
            accessTokenList.clear();
            refreshTokenList.clear();
        }
        //可以同时登录，则添加到列表中即可
        accessTokenList.add(String.valueOf(token.get("token")));
        refreshTokenList.add(String.valueOf(token.get("refreshToken")));
        redisTemplate.opsForValue().set(accessTokenKey, accessTokenList);
        redisUtil.setExpire(accessTokenKey, this.expire);
        redisTemplate.opsForValue().set(refreshTokenKey, refreshTokenList);
        redisUtil.setExpire(refreshTokenKey, this.expire * 6);
        Map<String, Object> data = new HashMap<String, Object>();
        // 将一些数据存储到redis
        for (String key : redisData.keySet()) {
            data.put(key, redisData.get(key));
        }
        data.put("id", id);
        data.put("uuid", uuid);
        data.put("token", token.get("token"));
        data.put("refreshToken", token.get("refreshToken"));
        redisTemplate.opsForValue().set(token.get("token"), data);
        redisUtil.setExpire(String.valueOf(token.get("token")), this.expire);
        redisTemplate.opsForValue().set(token.get("refreshToken"), data);
        redisUtil.setExpire(String.valueOf(token.get("refreshToken")), this.expire * 6);
    }

    /**
     * 刷新token
     * @param refreshToken
     * @param model
     * @return
     * @throws ServiceException
     */
    public HashMap<String, Object> refreshToken(String refreshToken,String model) throws ServiceException {
        try {
            Map<String, Object> res = this.checkToken(refreshToken,model, true);
            // 将一些数据存储到redis
            for (String key : res.keySet()) {
                redisData.put(key, res.get(key));
            }
            //清除token
            this.clearToken((String) res.get("uuid"), (String) res.get("token"), (String) res.get("refreshToken"));
            return this.getToken((Integer) res.get("id"), (String) res.get("uuid"),model);
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e.getCode());
        }
    }

    /**
     * 退出登录
     * @param token
     * @param model
     * @throws ServiceException
     */
    public void logOut(String token,String model) throws ServiceException {
        try {
            Map<String, Object> res = this.checkToken(token,model, false);
            //清除token
            this.clearToken((String) res.get("uuid"), (String) res.get("token"), (String) res.get("refreshToken"));
        } catch (ServiceException e) {
            throw new ServiceException(e.getMessage(), e.getCode());
        }
    }

    /**
     * 删除某个token
     *
     * @param uuid
     * @param token
     * @param refreshToken
     */
    public void clearToken(String uuid, String token, String refreshToken) {
        String accessTokenKey = this.prefix + "access_" + uuid;
        String refreshTokenKey = this.prefix + "refresh_" + uuid;
        //从set中删除那个token
        HashSet<String> accessTokenList = redisUtil.isExpire(accessTokenKey) ? new HashSet<String>() : JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(accessTokenKey)), HashSet.class);
        HashSet<String> refreshTokenList = redisUtil.isExpire(refreshTokenKey) ? new HashSet<String>() : JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(refreshTokenKey)), HashSet.class);
        accessTokenList.remove(token);
        refreshTokenList.remove(refreshToken);
        //删除redis中的token
        if(!redisUtil.isExpire(token)){
            redisTemplate.delete(token);
        }
        if(!redisUtil.isExpire(refreshToken)){
            redisTemplate.delete(refreshToken);
        }
        //重新存储到redis
        redisTemplate.opsForValue().set(accessTokenKey, accessTokenList);
        redisUtil.setExpire(accessTokenKey, this.expire);
        redisTemplate.opsForValue().set(refreshTokenKey, refreshTokenList);
        redisUtil.setExpire(refreshTokenKey, this.expire * 6);
    }

    /**
     * 失效用户所有token
     * @param uuid
     */
    public void clearAllToken(String uuid) {
        String accessTokenKey = this.prefix + "access_" + uuid;
        String refreshTokenKey = this.prefix + "refresh_" + uuid;
        HashSet<String> accessTokenList = redisUtil.isExpire(accessTokenKey) ? new HashSet<String>() : JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(accessTokenKey)), HashSet.class);
        HashSet<String> refreshTokenList = redisUtil.isExpire(refreshTokenKey) ? new HashSet<String>() : JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(refreshTokenKey)), HashSet.class);
        for (String i : accessTokenList) {
            if(!redisUtil.isExpire(i)){
                redisTemplate.delete(i);
            }
        }
        for (String i : refreshTokenList) {
            if(!redisUtil.isExpire(i)){
                redisTemplate.delete(i);
            }
        }
        redisTemplate.delete(accessTokenKey);
        redisTemplate.delete(refreshTokenKey);
    }

    /**
     * 验证token
     * @param token
     * @param model
     * @param isRefresh
     * @return
     * @throws ServiceException
     */
    public Map<String, Object> checkToken(String token,String model, boolean isRefresh) throws ServiceException {
        //验签
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(this.appSecret)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new ServiceException("无效token", -1);
        }
        //获取token类型
        String type = String.valueOf(JWT.decode(token).getClaim("type").asString());
        String tokenKey;
        if (isRefresh) {
            if (!"refreshToken".equals(type)) {
                throw new ServiceException("无效Token,非refreshToken", -1);
            }
            tokenKey = this.prefix + "refresh_";
        } else {
            if (!"token".equals(type)) {
                throw new ServiceException("无效Token,非AccessToken", -1);
            }
            tokenKey = this.prefix + "access_";
        }
        //获取model 模块是否响应
        String tokenModel = String.valueOf(JWT.decode(token).getClaim("model").asString());
        if (!model.equals(tokenModel)) {
            throw new ServiceException("无效Token,权限越界", -1);
        }
        if (redisUtil.isExpire(token)) {
            throw new ServiceException("Token过期", -1);
        }
        //获取redis中的内容
        Map<String, Object> resultMap = (Map<String, Object>) redisTemplate.opsForValue().get(token);
        //判断token是否生效
        tokenKey = tokenKey + resultMap.get("uuid");
        if (redisUtil.isExpire(tokenKey)) {
            throw new ServiceException("无效Token", -1);
        }
        HashSet<String> tokenList = redisUtil.isExpire(tokenKey) ? new HashSet<String>() : JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get(tokenKey)), HashSet.class);
        if (tokenList.contains(token) == false) {
            throw new ServiceException("无效Token", -1);
        }
        return resultMap;
    }

}
