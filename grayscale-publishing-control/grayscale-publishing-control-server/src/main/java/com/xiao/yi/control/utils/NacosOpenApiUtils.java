package com.xiao.yi.control.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xiao.yi.control.data.entry.Resource;
import com.xiao.yi.control.data.entry.ResourceNode;
import com.xiao.yi.control.data.model.page.PageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiaoyi
 * @since 2023/2/27
 */
@Component
public class NacosOpenApiUtils {

    private static final String SERVICE_LIST_API = "/nacos/v1/ns/service/list";

    private static final String UPDATE_SERVICE_API = "/nacos/v1/ns/service";

    private static final String SERVICE_INSTANCE_API = "/nacos/v1/ns/instance/list";

    private static final String UPDATE_SERVICE_INSTANCE_API = "/nacos/v1/ns/instance";

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    private String host;

    @Value("${spring.cloud.nacos.discovery.namespace}")
    private String namespace;

    public JSONObject serviceList(PageModel pageModel) {
        String url = String.join("",
                host,
                SERVICE_LIST_API,
                "?",
                "pageNo=",
                String.valueOf(pageModel.getPageNo()),
                "&pageSize=",
                String.valueOf(pageModel.getPageSize()),
                "&namespace=",
                namespace);
        return validateResponse(HttpRequest.get(url));
    }

    public JSONObject service(String resourceName) {
        String url = String.join("",
                host,
                SERVICE_INSTANCE_API,
                "?",
                "serviceName=",
                resourceName,
                "&namespace=",
                namespace,
                "&healthyOnly=",
                "true");
        return validateResponse(HttpRequest.get(url));
    }

    public void updateServiceMetadata(Resource resource) {
        String url = String.join("",
                host,
                UPDATE_SERVICE_API,
                "?",
                "serviceName=",
                resource.getResourceName(),
                "&namespace=",
                namespace,
                "&metadata=",
                JSONUtil.toJsonStr(resource),
                "&protectThreshold=",
                "0");
        validateVoidResponse(HttpRequest.put(url));
    }

    public void updateServiceInstanceMetadata(ResourceNode node) {
        String url = String.join("",
                host,
                UPDATE_SERVICE_INSTANCE_API,
                "?",
                "serviceName=",
                node.getResourceName(),
                "&namespace=",
                namespace,
                "&ip=",
                node.getIp(),
                "&port=",
                String.valueOf(node.getPort()),
                "&metadata=",
                JSONUtil.toJsonStr(node));
        validateVoidResponse(HttpRequest.put(url));
    }

    public JSONObject validateResponse(HttpRequest request) {
        try (HttpResponse response = request.execute()) {
            if (response.isOk()) {
                String body = response.body();
                return JSONUtil.parseObj(body);
            }
            throw new RuntimeException("查询服务列表失败: " + response.body());
        } catch (Exception e) {
            throw new RuntimeException("查询服务列表失败", e);
        }
    }

    public void validateVoidResponse(HttpRequest request) {
        try (HttpResponse response = request.execute();) {
            if (!response.isOk()) {
                throw new RuntimeException("更新服务实例失败: " + response.body());
            }
        } catch (Exception e) {
            throw new RuntimeException("更新服务实例失败", e);
        }
    }

}
