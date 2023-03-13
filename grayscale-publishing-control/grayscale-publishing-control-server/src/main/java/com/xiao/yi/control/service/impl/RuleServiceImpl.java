package com.xiao.yi.control.service.impl;

import com.xiao.yi.control.entry.Rule;
import com.xiao.yi.control.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xiaoyi
 * @since 2023/3/2
 */
@Service
public class RuleServiceImpl implements RuleService {

    private static final String RULE_REDIS_KEY = "RULE_REDIS_KEY";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void setRules(Rule rule) {
        redisTemplate.opsForValue().set(RULE_REDIS_KEY, rule);
    }
}
