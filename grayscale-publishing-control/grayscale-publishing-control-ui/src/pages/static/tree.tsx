import React, {useEffect, useState} from "react";
import {ProCard} from "@ant-design/pro-components";
import {childResources, getServerHost, rootResources} from "@/services/com.xiao.yi/api";
import {Button, Descriptions, Space, Tree, Upload, UploadProps} from "antd";
import {UploadOutlined} from "@ant-design/icons";

interface DataNode {
  title: string;
  key: string;
  isLeaf?: boolean;
  children?: DataNode[];
}

const StaticResourceList: React.FC = () => {

  const [normalTree, setNormalTree] = useState<DataNode[] | []>([])
  const [grayscaleTree, setGrayscaleTree] = useState<DataNode[] | []>([])
  const [serverHost, setServerHost] = useState<string>("")

  useEffect(() => {
    loadRootResource()

    getServerHost().then(response => {
      if (response.success) {
        setServerHost(response.data)
      }
    })
  }, [])

  const loadRootResource = () => {
    rootResources().then((rootResourceResponse) => {
      setNormalTree(rootResourceResponse.data.normal.map((item) => {
        return {
          title: item.name,
          key: item.name,
          isLeaf: !item.directory
        }
      }))
      setGrayscaleTree(rootResourceResponse.data.grayscale.map((item) => {
        return {
          title: item.name,
          key: item.name,
          isLeaf: !item.directory
        }
      }))
    })
  }

  const updateTreeData = (list: DataNode[], key: React.Key, children: DataNode[]): DataNode[] =>
    list.map((node) => {
      if (node.key === key) {
        return {
          ...node,
          children,
        };
      }
      if (node.children) {
        return {
          ...node,
          children: updateTreeData(node.children, key, children),
        };
      }
      return node;
    });

  const loadNormalChild = ({key, children}: any) => {
    return loadChild(key, children, 'normal')
  }

  const loadGrayscaleChild = ({key, children}: any) => {
    return loadChild(key, children, 'grayscale')
  }

  const loadChild = (key: string, children: any, type: string) => {
    return new Promise<void>((resolve) => {
      if (children) {
        resolve();
        return;
      }

      childResources(key, type).then((response) => {
        if (response.success) {
          switch (type) {
            case 'normal':
              setNormalTree((origin) =>
                updateTreeData(origin, key, response.rows.map((item) => {
                  return {
                    title: item.name,
                    key: key + "/" + item.name,
                    isLeaf: !item.directory
                  }
                })))
              break;
            case 'grayscale':
              setGrayscaleTree((origin) =>
                updateTreeData(origin, key, response.rows.map((item) => {
                  return {
                    title: item.name,
                    key: key + "/" + item.name,
                    isLeaf: !item.directory
                  }
                })))
              break;
            default:
              break;
          }
        }
        resolve()
      })
    })
  }

  const uploadProps: UploadProps = {
    name: 'file',
    action: serverHost + '/static/resource/deployGrayscaleStaticResource',
    maxCount: 1,
    headers: {
      // @ts-ignore
      "X-Requested-With": null
    },
    onChange(info) {
      if (info.file.response.success) {
        loadRootResource()
      }
    },
  };

  return (
    <>
      <Space direction="vertical" size="middle" style={{display: 'flex'}}>
        <Space wrap>
          <Upload {...uploadProps}>
            <Button icon={<UploadOutlined/>}>部署测试资源</Button>
          </Upload>
        </Space>
        <ProCard gutter={8} title={"资源目录"}>
          <ProCard colSpan={12}>
            <Descriptions title="正式资源"/>
            <Tree treeData={normalTree} blockNode showLine={true} loadData={loadNormalChild}/>
          </ProCard>
          <ProCard colSpan={12}>
            <Descriptions title="测试资源"/>
            <Tree treeData={grayscaleTree} blockNode showLine={true} loadData={loadGrayscaleChild}/>
          </ProCard>
        </ProCard>
      </Space>
    </>
  )
}

export default StaticResourceList;
