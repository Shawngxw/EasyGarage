import React, {useState} from 'react';
import {Descriptions, Button, Modal, Form, Input, Table} from "antd";
import {useAddCarMutation, useDeleteCarMutation, useEditCurrentUserMutation, useGetCurrentUserCarsQuery, useGetCurrentUserQuery} from "../api/api";

// import ProfileHeader from "../../../public/profile_header.jpg"


function Profile() {
    const {data: user, isFetching} = useGetCurrentUserQuery()
    const {data: cars, isFetching: isCarFetching} = useGetCurrentUserCarsQuery()
    const labelStyle = {fontWeight: "bold"}
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [form] = Form.useForm()
    const [form2] = Form.useForm()
    const [edit] = useEditCurrentUserMutation()
    const [addCar] = useAddCarMutation()
    const [deleteCar] = useDeleteCarMutation()
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
                    return "Parked"
                } else if (status === 1) {
                    return "Not Parked"
                } else {
                    return "Reserved"
                }
            }
        },
        {
            title: "Operation",
            key: "Operation",
            render: (_, record) => {
                const handleDeleteCar = () => {
                    deleteCar({id: record.id})
                }
                return <Button type="primary" danger onClick={handleDeleteCar}>Delete</Button>
            }
        }

    ];

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
    const handleAddCar = () => {
        form2.validateFields()
            .then((values) => {
                addCar({
                    name: values.name, type: parseInt(values.type), status: parseInt(values.status)
                })
                form.resetFields()
                setIsAddModalOpen(false);
            })

    }
    const handleAddCarCancel = () => {
        setIsAddModalOpen(false)
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
                                    <Table
                                        columns={columns}
                                        dataSource={cars}
                                        title={() => {
                                            return <div style={{
                                                display: "flex",
                                                justifyContent: "flex-end",
                                                paddingRight: 20
                                            }}><Button type="primary" onClick={() => setIsAddModalOpen(true)}>添加</Button></div>
                                        }}
                                    />
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
            {/*Add Car*/}
            <Modal title="Add Car" visible={isAddModalOpen} onOk={handleAddCar} onCancel={handleAddCarCancel} centered>
                <Form labelCol={{span: 8}} form={form2}>
                    <Form.Item
                        label="Car Name"
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
                        label="Type"
                        name="type"
                        rules={[
                            {
                                required: true,
                            },
                        ]}
                    >
                        <Input/>
                    </Form.Item>
                    <Form.Item
                        label="Status"
                        name="status"
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