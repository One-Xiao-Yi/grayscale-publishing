package com.xiao.yi.route;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.cloud.nacos.ribbon.NacosServerList;
import com.netflix.client.config.DefaultClientConfigImpl;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.xiao.yi.constants.enums.NodeTypeEnum;
import org.springframework.beans.BeanUtils;

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
        this.serviceId = config.getClientName();
        this.originalConfig = config;
    }


    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        DefaultClientConfigImpl nacosServerListClientConfig = new DefaultClientConfigImpl();
        BeanUtils.copyProperties(iClientConfig, nacosServerListClientConfig);
        nacosServerListClientConfig.setClientName(iClientConfig.getClientName().replace(GrayscalePublishingConstants.GRAYSCALE_RESOURCE_NAME_PLACEHOLDER, ""));
        nacosServerList.initWithNiwsConfig(nacosServerListClientConfig);
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
        List<NacosServer> filtered = servers.stream().filter(item -> {
            if (item != null) {
                Map<String, String> metadata = ((NacosServer) item).getMetadata();
                if (serviceId.endsWith(GrayscalePublishingConstants.GRAYSCALE_RESOURCE_NAME_PLACEHOLDER)) {
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
