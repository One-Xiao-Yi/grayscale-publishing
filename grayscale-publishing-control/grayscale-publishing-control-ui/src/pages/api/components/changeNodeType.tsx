import {message, Modal, Radio, RadioChangeEvent} from "antd";
import React, {useState} from "react";
import {changeResourceType} from "@/services/com.xiao.yi/api";

const ChangeNodeType: React.FC<{ open: boolean, setOpen: (open: boolean) => void, node: API.ApiResourceNode, handleOkCallback: () => void }> = (props) => {

  const [nodeType, setNodeType] = useState<number>(-1);
  const [confirmLoading, setConfirmLoading] = useState<boolean>(false)

  const nodeTypeChange = (e: RadioChangeEvent) => {
    setNodeType(e.target.value)
  }

  const handleOk = () => {
    setConfirmLoading(true)
    let changeNode = props.node
    changeNode.nodeType = nodeType
    changeResourceType(changeNode).then((res) => {
      if (res.success) {
        message.success("修改成功")
      } else {
        message.error("修改失败: " + res.msg)
      }
    }).finally(() => {
      setConfirmLoading(false)
      props.setOpen(false)
      props.handleOkCallback()
    })
  }

  return (
    <>
      <Modal
        title="设置节点类型"
        open={props.open}
        onOk={handleOk}
        confirmLoading={confirmLoading}
      >
        <Radio.Group onChange={nodeTypeChange} value={nodeType}>
          <Radio value={1}>测试节点</Radio>
          <Radio value={2}>正式节点</Radio>
        </Radio.Group>
      </Modal>
    </>
  )

}

export default ChangeNodeType
