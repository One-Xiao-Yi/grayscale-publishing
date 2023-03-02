import React, {useEffect, useState} from 'react';
import {getResourceList} from "@/services/com.xiao.yi/api";
import {Button, message, Space} from "antd";
import {history} from "@umijs/max";
import {ProList} from "@ant-design/pro-components";
import {SetRules} from "@/pages/resource/components/setRules";

const ResourceList: React.FC = () => {

  const [resourceList, setResourceList] = useState<any>({})
  const [resourceNames, setResourceNames] = useState<{ title: string, subTitle?: string, nodes: API.Resource }[]>([])
  const [ruleOpen, setRuleOpen] = useState(false)

  const toDetail = (resource: API.Resource) => {
    history.push({
      pathname: '/resource/node',
    }, resource)
  }

  const divideRule = () => {
    setRuleOpen(true)
  }

  useEffect(() => {
    getResourceList({"resourceType": 1, "pageNo": 1, "pageSize": 10}).then((res: API.ResourceListResult) => {
      if (res.success) {
        setResourceList(res.rows)
        const resourceArray: { title: string, subTitle?: string, nodes: API.Resource }[] = []
        resourceArray.push(...resourceNames)
        for (const item of res.rows) {
          resourceArray.push({title: item.resourceName, nodes: item as API.Resource})
        }
        setResourceNames(resourceArray)
      } else {
        message.error(res.msg)
      }
    })
  }, [])

  return (
    <>
      <Space direction="vertical" size="middle" style={{display: 'flex'}}>
        <Space wrap>
          <Button type={"primary"} onClick={() => divideRule()}>分配策略</Button>
        </Space>
        <ProList<any>
          pagination={{
            defaultPageSize: 8,
            showSizeChanger: false,
          }}
          showActions="hover"
          rowSelection={{}}
          grid={{gutter: 16, column: 2}}
          onItem={(record: any) => {
            return {
              onClick: () => {
                toDetail(record.nodes);
              },
            };
          }}
          metas={{
            title: {},
            subTitle: {},
            actions: {
              cardActionProps: "extra",
            },
          }}
          headerTitle="资源列表"
          dataSource={resourceNames}
        />
      </Space>
      <SetRules open={ruleOpen} setOpen={(open) => setRuleOpen(open)}/>
    </>
  );
}

export default ResourceList
