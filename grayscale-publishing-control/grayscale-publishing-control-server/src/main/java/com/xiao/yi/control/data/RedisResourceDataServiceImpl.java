package com.xiao.yi.control.data;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.xiao.yi.control.data.api.ResourceDataService;
import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.model.reponse.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiaoyi
 * @since 2023/2/16
 */
@Component
public class RedisResourceDataServiceImpl implements ResourceDataService {

    private static final String RESOURCE_REDIS_KEY = "publishing-resources-key";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseModel<Resource> resources(Resource resource) {
        Integer current = resource.getPageNo();
        Integer size = resource.getPageSize();
        Long total = redisTemplate.opsForList().size(RESOURCE_REDIS_KEY);
        if (null == total || 0L == total) {
            ResponseModel.success();
        }
        List<Object> list;
        if (null != current && null != size) {
            list = redisTemplate.opsForList().range(RESOURCE_REDIS_KEY, (long) (current - 1) * size, (long) current * size);
        } else {
            list = redisTemplate.opsForList().range(RESOURCE_REDIS_KEY, 0, total);
        }
        List<Resource> resources = JSONUtil.toList(JSONUtil.toJsonStr(list), Resource.class);
        ResponseModel<Resource> responseModel = ResponseModel.success(resources);
        responseModel.setTotal(total);
        return responseModel;
    }

    @Override
    public void put(Resource resource) {
        Boolean exists = redisTemplate.hasKey(RESOURCE_REDIS_KEY + "-" + resource.getResourceName() + "-" + resource.getResourceType());
        if (Boolean.FALSE.equals(exists)) {
            Resource simpleResource = new Resource();
            simpleResource.setResourceName(resource.getResourceName());
            simpleResource.setResourceType(resource.getResourceType());
            redisTemplate.opsForList().rightPush(RESOURCE_REDIS_KEY, simpleResource);
        }
        redisTemplate.opsForValue().set(RESOURCE_REDIS_KEY + "-" + resource.getResourceName() + "-" + resource.getResourceType(), resource);
    }

    @Override
    public Resource get(String resourceName, Integer resourceType) {
        Object resourceObj = redisTemplate.opsForValue().get(RESOURCE_REDIS_KEY + "-" + resourceName + "-" + resourceType);
        if (null != resourceObj) {
            return BeanUtil.copyProperties(resourceObj, Resource.class);
        }
        return null;
    }
}
