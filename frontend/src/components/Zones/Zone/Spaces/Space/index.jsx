import React, {useContext, useState} from 'react';
import {Col, DatePicker, Descriptions, Form, Input, Modal, Select, Tooltip} from "antd";
import styles from "./index.module.less"
import {ZoneTypeCtx} from "../../../utils";
import {useBeginParkMutation, useGetCurrentUserCarsQuery} from "../../../../../api/api";

const {Option} = Select
const {RangePicker} = DatePicker;

function Space(props) {
    const {id, status, floor, number, normalPrice, latePrice} = props
    let {data: cars, isFetching, refetch} = useGetCurrentUserCarsQuery()
    let space_style = styles.colNotPark
    switch (status) {
        case 1:
            space_style = styles.colNotPark
            break
        case 2:
            space_style = styles.colParked
            break
        case 3:
            space_style = styles.colReserve
            break
        default:
            space_style = styles.colNotPark
    }
    const [isModalOpen, setIsModalOpen] = useState(false)
    const [form] = Form.useForm()
    const zoneTypeCtx = useContext(ZoneTypeCtx)
    const [beginPark] = useBeginParkMutation()
    const handleOk = () => {
        form
            .validateFields()
            .then((values) => {
                if (zoneTypeCtx.zoneType === "Parking") {
                    beginPark({
                        floor,
                        number,
                        create: 1,
                        vehicle_name: values.ownCars
                    })
                } else {
                    console.log(values)
                }

                form.resetFields();
                setIsModalOpen(false);
            })
            .catch((info) => {
                console.log('Validate Failed:', info);
            });
    }
    const handleCancel = () => {
        setIsModalOpen(false);
    }
    return (
        <>
            <Col
                span={4}
                className={space_style}
                onClick={() => {
                    refetch()
                    setIsModalOpen(true);
                }}
            >
                <Tooltip
                    title={
                        <div>
                            <Descriptions
                                title={<div style={{color: "white"}}>{status === 1 ? `Vehicle: ${"null"}` : "Free"}</div>}
                                column={1}
                            >
                                {
                                    Object.entries(props).map(item => {
                                        // if (item[0] === vehicle_parking_id) return ""
                                        if (!item[1] || item[1] === true) return ""
                                        return <Descriptions.Item
                                            label={item[0]}
                                            key={item[0]}
                                            contentStyle={{color: "white"}}
                                            labelStyle={{color: "white"}}
                                        >{item[1]}</Descriptions.Item>
                                    })
                                }
                            </Descriptions>
                        </div>
                    }
                    placement="bottom"
                >
                    <div className="title">{`${floor}-${number}`}</div>
                </Tooltip>
            </Col>
            <Modal
                title={zoneTypeCtx.zoneType}
                visible={isModalOpen}
                onOk={handleOk}
                onCancel={handleCancel}
                centered
            >
                {zoneTypeCtx.zoneType === "Parking" ? (
                    !isFetching ? (
                        <Form
                            form={form}
                            layout="vertical"
                            name="form_in_modal"
                            initialValues={{
                                modifier: 'public',
                            }}
                        >
                            <Form.Item
                                name="ownCars"
                                label="Own Cars"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Please input which car you want to park!',
                                    },
                                ]}
                            >
                                <Select
                                    placeholder="Select a option and change input text above"
                                    allowClear
                                >
                                    {cars.filter(car => {
                                        return car.status !== 1
                                    }).map(car => {
                                        return <Option value={car.name}>{car.name}</Option>
                                    })}
                                </Select>
                            </Form.Item>
                        </Form>
                    ) : "Loading..."
                ) : (
                    !isFetching ? (
                        <Form
                            form={form}
                            layout="vertical"
                            name="form_in_modal"
                            initialValues={{
                                modifier: 'public',
                            }}
                        >
                            <Form.Item
                                name="ownCars"
                                label="Own Cars"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Please input which car you want to park!',
                                    },
                                ]}
                            >
                                <Select
                                    placeholder="Select a option and change input text above"
                                    allowClear
                                >
                                    {cars.filter(car => {
                                        return car.status !== 1
                                    }).map(car => {
                                        return <Option value={car.name}>{car.name}</Option>
                                    })}
                                </Select>
                            </Form.Item>
                            <Form.Item name="range-time-picker"
                                       label="Reserve Range"
                                       rules={[{type: 'array', required: true, message: 'Please select time!'}]}>
                                <RangePicker showTime format="YYYY-MM-DD HH:mm:ss"/>
                            </Form.Item>
                        </Form>
                    ) : "Loading..."
                )}


            </Modal>
        </>

    );
}

export default Space;