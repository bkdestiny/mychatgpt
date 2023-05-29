package com.mychatgpt.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mychatgpt.entity.User;
import com.mychatgpt.mapper.UserMapper;
import com.mychatgpt.service.UserService;
import com.mychatgpt.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private CacheClient client;
    @Resource
    private RedisIdWorker redisIdWorker;
    @Override
    public Result login(String code, String rawData, String signature) {
        JSONObject rawDataJson= JSONUtil.parseObj (rawData);
        //根据code从微信服务器获取open_id和session_key
        JSONObject sessionKeyOrOpenIdJson = WechatUtil.getSessionKeyOrOpenId (code);
        String open_id = sessionKeyOrOpenIdJson.getStr ("openid");
        String session_key = sessionKeyOrOpenIdJson.getStr ("session_key");
        //校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String s = DigestUtils.sha1Hex (rawData + session_key);
        if(!signature.equals (s)){
            return Result.fail ("No");
        }
        //根据open_id查询用户是否存在
        User user = query ().eq ("open_id",open_id).one ();
        //用户不存在，注册新用户
        if(user==null){
            user = new User ();
            user.setOpenId (open_id);
            user.setSessionKey (session_key);
            user.setNickName (rawDataJson.getStr ("nickName"));
            user.setAvatarUrl (rawDataJson.getStr("avatarUrl"));
            LocalDateTime now = LocalDateTime.now ();
            user.setLastVisitTime (now);
            user.setCreateTime (now);
            save (user);
        }else{
            //用户存在，更新最后登录时间
            user.setLastVisitTime (LocalDateTime.now ());
            update ().update (user);
        }
        //生产tokenKey
        String token= UUID.fastUUID ().toString (true);
        String tokenKey=RedisContent.TOKEN_KEY+token;
        //写入缓存
        stringRedisTemplate.opsForValue ().set (tokenKey,JSONUtil.toJsonStr (user),RedisContent.TOKEN_TTL,TimeUnit.DAYS);
        //返回token给客户端保存
        return Result.ok (token);
    }

    @Override
    public Result currentUser() {
        Long id=UserHolder.getUser ().getId ();

        User user = getById (id);
        log.info("id-->{},user-->{}",id,user);
        user.setLastVisitTime (null);
        user.setCreateTime (null);
        return Result.ok (user);
    }

}
