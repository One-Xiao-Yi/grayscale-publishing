package com.xiao.yi.route;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.cloud.nacos.ribbon.NacosServerList;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.xiao.yi.control.data.enums.NodeTypeEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xiaoyi
 * @since 2023/3/2
 */
public class GrayscalePublishingNacosServerList extends AbstractServerList<NacosServer> {

    private NacosServerList nacosServerList;

    private String serviceId;

    private IClientConfig originalConfig;

    public GrayscalePublishingNacosServerList(IClientConfig config,
                                              NacosDiscoveryProperties nacosDiscoveryProperties) {
        nacosServerList = new NacosServerList(nacosDiscoveryProperties);
    }


    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        nacosServerList.initWithNiwsConfig(iClientConfig);
    }

    @Override
    public List<NacosServer> getInitialListOfServers() {
        List<NacosServer> servers = nacosServerList.getInitialListOfServers();
        return grayscaleFilter(servers);
    }

    @Override
    public List<NacosServer> getUpdatedListOfServers() {
        List<NacosServer> servers = nacosServerList.getUpdatedListOfServers();
        return grayscaleFilter(servers);
    }

    private List<NacosServer> grayscaleFilter(List<NacosServer> servers) {
        if (null == servers || servers.size() == 0) {
            return servers;
        }
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String header = request.getHeader(GrayscalePublishingConstants.GRAYSCALE_HEADER);
        List<NacosServer> filtered = servers.stream().filter(item -> {
            if (item != null) {
                Map<String, String> metadata = ((NacosServer) item).getMetadata();
                if (GrayscalePublishingConstants.GRAYSCALE_HEADER_VALUE.equals(header)) {
                    return metadata.containsKey(GrayscalePublishingConstants.METADATA_GRAYSCALE_KEY)
                            && NodeTypeEnum.of(Integer.parseInt(metadata.get(GrayscalePublishingConstants.METADATA_GRAYSCALE_KEY))).equals(NodeTypeEnum.DEV);
                }
                return !metadata.containsKey(GrayscalePublishingConstants.METADATA_GRAYSCALE_KEY)
                        || !NodeTypeEnum.of(Integer.parseInt(metadata.get(GrayscalePublishingConstants.METADATA_GRAYSCALE_KEY))).equals(NodeTypeEnum.DEV);
            }
            return true;
        }).collect(Collectors.toList());
        if (filtered.size() == 0) {
            return servers;
        }
        return filtered;
    }
}
