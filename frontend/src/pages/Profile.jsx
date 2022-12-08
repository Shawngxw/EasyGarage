import React, {useState} from 'react';
import {Descriptions, Button, Modal, Form, Input, Table} from "antd";
import {useEditCurrentUserMutation, useGetCurrentUserCarsQuery, useGetCurrentUserQuery} from "../api/api";

// import ProfileHeader from "../../../public/profile_header.jpg"


const columns = [
    {
        title: 'ID',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: 'Name',
        dataIndex: 'name',
        key: 'name',
    },
    {
        title: 'Type',
        dataIndex: 'type',
        key: 'type',
    },
    {
        title: "Status",
        dataIndex: "status",
        key: "status",
        render: (_, record) => {
            const {status} = record
            if (status === 0) {
                return "Not Parked"
            } else if (status === 1) {
                return "Parked"
            }
        }
    }

];

function Profile() {
    const {data: user, isFetching} = useGetCurrentUserQuery()
    const {data: cars, isFetching: isCarFetching} = useGetCurrentUserCarsQuery()
    const labelStyle = {fontWeight: "bold"}
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [form] = Form.useForm()
    const [edit] = useEditCurrentUserMutation()
    const handleEdit = () => {
        setIsModalOpen(true)
    }
    const handleOk = () => {
        form.validateFields()
            .then((values) => {
                edit({original_email: user.email, ...values})
                form.resetFields()
                console.log(values)
                setIsModalOpen(false);
            })

    }
    const handleCancel = () => {
        setIsModalOpen(false);

    }
    return !isFetching ? (
        <div className="profile">
            <div className="profile-cover">
                <img src={"./profile_cover.jpg"} alt="null"/>
            </div>
            <div className="profile-info">
                <div className="icon">
                    <img src={"./profile_header.jpg"} alt="null"/>
                </div>
                <div className="content">
                    <Descriptions title={<div className="content-title">{user.name}</div>} column={1}
                                  extra={<Button type={"primary"} onClick={handleEdit}>Edit</Button>}>
                        <Descriptions.Item label="Email" labelStyle={labelStyle}>{user.email}</Descriptions.Item>
                        <Descriptions.Item label="Description" labelStyle={labelStyle}>{user.description}</Descriptions.Item>
                        <Descriptions.Item label="My Cars" labelStyle={labelStyle}>
                            <div className="profile-cars">
                                {!isCarFetching ? (
                                    <Table columns={columns} dataSource={cars}/>
                                ) : "Loading..."}
                            </div>
                        </Descriptions.Item>
                    </Descriptions>

                </div>
            </div>


            <Modal title="Edit Info" visible={isModalOpen} onOk={handleOk} onCancel={handleCancel} centered>
                <Form labelCol={{span: 8}} form={form}>
                    <Form.Item
                        label="New Email"
                        name="new_email"
                        rules={[
                            {
                                required: true,
                            },
                        ]}
                    >
                        <Input/>
                    </Form.Item>
                    <Form.Item
                        label="Name"
                        name="name"
                        rules={[
                            {
                                required: true,
                            },
                        ]}
                    >
                        <Input/>
                    </Form.Item>
                    <Form.Item
                        label="Password"
                        name="password"
                        rules={[
                            {
                                required: true,
                            },
                        ]}
                    >
                        <Input/>
                    </Form.Item>
                    <Form.Item
                        label="Description"
                        name="description"
                        rules={[
                            {
                                required: true,
                            },
                        ]}
                    >
                        <Input/>
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    ) : <div>Loading...</div>


}

export default Profile;