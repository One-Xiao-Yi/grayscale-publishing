import React, {useEffect, useState} from 'react';
import {Button, Space, Tag} from "antd";
import {useLocation} from "umi";
import {ProList} from "@ant-design/pro-components";
import ChangeNodeType from "@/pages/resource/components/changeNodeType";
import {SetRules} from "@/pages/resource/components/setRules";
import {getResource} from "@/services/com.xiao.yi/api";

const ResourceNode: React.FC = () => {

  const [nodeList, setNodeList] = useState<{ title: string, subTitle: any, actions: any, content: any, node: API.Node }[]>([])
  const [resource, setResource] = useState<API.Resource>({nodes: [], resourceName: "", resourceType: undefined})
  const location = useLocation()
  const [actionType, setActionType] = useState<number>(-1)
  const [selectNode, setSelectNode] = useState<API.Node>({});
  const [nodeTypeOpen, setNodeTypeOpen] = useState(false)


  const changeNodeType = (node: API.Node) => {
    setSelectNode(node)
    setActionType(1)
    setNodeTypeOpen(true)
  }

  const closeNode = (node: API.Node) => {
    setSelectNode(node)
  }

  const handleOk = () => {
    getResourceInfo(resource)
  }

  const getResourceInfo = (resource: API.Resource) => {
    console.log(resource)
    getResource(resource).then(res => {
      setNodeList(res.data?.nodes?.map(item => {
        const title = (item.ip || "") + ":" + (item.port || "");
        let content
        if (item.startTime) {
          content = "启动时间：" + new Date(item.startTime).toLocaleString();
        } else {
          content = ""
        }

        let subTitle
        let actions
        if (item.nodeType == 1) {
          subTitle = <Tag color="#40a9ff">测试节点</Tag>
          actions = [<a onClick={() => closeNode(item)}>关闭节点</a>]
        } else if (item.nodeType == 2) {
          subTitle = <Tag color="#fa541c">正式节点</Tag>
          actions = <a onClick={() => closeNode(item)}>关闭节点</a>
        } else {
          subTitle = <Tag color="#bfbfbf">未就绪</Tag>
          actions = <a onClick={() => changeNodeType(item)}>指定类型</a>
        }
        return {title: title, subTitle: subTitle, actions: actions, content: content, node: item}
      }) || [])
    })
  }

  useEffect(() => {
    const resource = location.state as API.Resource
    setResource(resource)
    getResourceInfo(resource)
  }, [])

  return (
    <>
      <Space direction="vertical" size="middle" style={{display: 'flex'}}>
        <ProList<any>
          pagination={{
            defaultPageSize: 8,
            showSizeChanger: false,
          }}
          showActions="hover"
          rowSelection={{}}
          grid={{gutter: 16, column: 2}}
          metas={{
            title: {},
            subTitle: {},
            content: {},
            actions: {
              cardActionProps: "extra",
            },
          }}
          headerTitle="节点列表"
          dataSource={nodeList}
        />
      </Space>
      {actionType == 1 && (
        <ChangeNodeType handleOkCallback={handleOk} open={nodeTypeOpen} setOpen={(open) => setNodeTypeOpen(open)}
                        node={selectNode}/>
      )}
      {actionType == 2 && (
        <ChangeNodeType handleOkCallback={handleOk} open={nodeTypeOpen} setOpen={(open) => setNodeTypeOpen(open)}
                        node={selectNode}/>
      )}
      {actionType == 3 && (
        <ChangeNodeType handleOkCallback={handleOk} open={nodeTypeOpen} setOpen={(open) => setNodeTypeOpen(open)}
                        node={selectNode}/>
      )}

    </>
  );
}

export default ResourceNode
