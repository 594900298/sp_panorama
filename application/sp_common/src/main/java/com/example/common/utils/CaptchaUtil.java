package com.example.common.utils;

import cn.hutool.captcha.ShearCaptcha;
import com.example.common.vo.CaptchaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码工具类
 */
@Component
public class CaptchaUtil {
    @Autowired
    private RedisTemplate redisTemplate = RedisUtil.redis;
    @Value("${app.captcha.prefix}")
    private String prefix; //验证码前缀
    @Value("${app.captcha.expire}")
    private long expire; //验证码过期时间
    @Value("${app.captcha.codeCount}")
    private int codeCount; //验证码数量
    @Value("${app.captcha.thickness}")
    private int thickness; //浓度

    public CaptchaUtil() {
    }

    public CaptchaUtil(RedisTemplate redisTemplate, String prefix, long expTime, int codeCount, int thickness) {
        this.redisTemplate = redisTemplate;
        this.prefix = prefix;
        this.expire = expTime;
        this.codeCount = codeCount;
        this.thickness = thickness;
    }

    /**
     * 获取图片验证码
     * @param codeSign
     */
    public CaptchaVO getCaptcha(String codeSign){
        if(codeSign.length()==0){
            codeSign = String.valueOf(UUID.randomUUID());
        }
        // 利用 hutool 工具，生成验证码图片资源
        ShearCaptcha captcha = cn.hutool.captcha.CaptchaUtil.createShearCaptcha(200, 100, this.codeCount, this.thickness);
        // 获得生成的验证码字符
        String code = captcha.getCode();
        // 存储到redis
        redisTemplate.opsForValue().set(this.prefix+codeSign,code,this.expire, TimeUnit.SECONDS);
        CaptchaVO vo = new CaptchaVO();
        vo.setCodeSign(codeSign);
        vo.setBase64CodeImg(captcha.getImageBase64Data());
        return vo;
    }

    /**
     * 验证验证码是否输入正确
     * @param codeSign
     * @param code
     * @return
     */
    public boolean checkCaptcha(String codeSign,String code){
        boolean isExpire = new RedisUtil().isExpire(this.prefix+codeSign);
        if(isExpire){
            return false;
        }
        String redisCode = String.valueOf(this.redisTemplate.opsForValue().get(this.prefix+codeSign));
        if (code.equals(redisCode)){
            //删除验证成功后的codeSign
            this.redisTemplate.delete(this.prefix+codeSign);
            return true;
        }else{
            return false;
        }
    }
}
