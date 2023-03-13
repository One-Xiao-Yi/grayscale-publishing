import React, {useEffect, useState} from "react";
import {message, Modal, Radio, RadioChangeEvent, Space} from "antd";
import {setRules} from "@/services/com.xiao.yi/api";
import {EditableProTable} from "@ant-design/pro-components";

type Resource = {
  id: string,
  name: string
}

export const SetRules: React.FC<{ open: boolean, setOpen: (open: boolean) => void }> = (props) => {

  const [ruleType, setRuleType] = useState<number>(-1);
  const [confirmLoading, setConfirmLoading] = useState<boolean>(false)
  const [resources, setResources] = useState<Resource[]>([])
  const [headerTitle, setHeaderTitle] = useState<string>("资源列表")
  const [clientIp, setClientIp] = useState<string>("")

  useEffect(() => {
    setClientIp(localStorage.getItem("clientIp") || "")
  }, [])

  const ruleTypeChange = (e: RadioChangeEvent) => {
    setRuleType(e.target.value)
    switch (ruleType) {
      case 1:
        setHeaderTitle("IP列表")
        break
      default:
        break
    }
  }

  const handleOk = () => {
    if (resources.length == 0) {
      message.error("未填写资源列表")
      return
    }
    setConfirmLoading(true)
    setRules({
      ruleType: ruleType,
      resources: resources.map(item => item.name).filter(item => item && item != "")
    }).then((res) => {
      if (res.success) {
        message.success("修改成功")
      } else {
        message.error("修改失败: " + res.msg)
      }
    }).finally(() => {
      setConfirmLoading(false)
      props.setOpen(false)
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
        <Space direction="vertical" size="middle" style={{display: 'flex'}}>
          <Radio.Group onChange={ruleTypeChange} value={ruleType}>
            <Radio value={1}>IP</Radio>
            {/*<Radio value={2}>用户</Radio>*/}
            {/*<Radio value={3}>分流</Radio>*/}
          </Radio.Group>
          {ruleType == 1 && "本机ip：" + clientIp}
          <EditableProTable<Resource>
            rowKey={"id"}
            headerTitle={headerTitle}
            recordCreatorProps={{
              position: 'bottom',
              record: {id: (Math.random() * 1000000).toFixed(0), name: ""}
            }}
            value={resources}
            onChange={(res) => {
              setResources(res as Resource[])
            }}
            columns={[
              {
                title: "名称",
                dataIndex: 'name',
              },
              {
                title: '操作',
                valueType: 'option',
                width: 200,
                render: (text, record, _, action) => [
                  <a
                    key="editable"
                    onClick={() => {
                      action?.startEditable?.(record.id);
                    }}
                  >
                    编辑
                  </a>,
                  <a
                    key="delete"
                    onClick={() => {
                      setResources(resources.filter((item) => item.id !== record.id))
                    }}
                  >
                    删除
                  </a>,
                ],
              }]}>

          </EditableProTable>
          {/*{*/}
          {/*  <Space direction="vertical" size="middle" style={{ display: 'flex' }}>*/}
          {/*    {resources.map(item => <Input key={item.index} onChange={(e) => inputChange(item.index, e.target.value)}/>)}*/}
          {/*    <Button onClick={addResources} shape="circle" icon={<PlusCircleOutlined />} />*/}
          {/*  </Space>*/}
          {/*}*/}
        </Space>
      </Modal>

    </>
  )

}
