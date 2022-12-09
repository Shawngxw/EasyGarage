import React, {useContext, useState} from 'react';
import {Col, DatePicker, Descriptions, Form, Input, Modal, Select, Tooltip} from "antd";
import styles from "./index.module.less"
import {ZoneTypeCtx} from "../../../utils";
import {useBeginParkMutation, useGetCurrentUserCarsQuery, useMakeReserveMutation} from "../../../../../api/api";

const {Option} = Select
const {RangePicker} = DatePicker;

function Space(props) {
    const {id, status, floor, number, normalPrice, latePrice, vehicleID} = props
    let {data: cars, isFetching, refetch} = useGetCurrentUserCarsQuery()
    const [makeReserve] = useMakeReserveMutation()
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
                    const obj = {
                        vehicle_name: values.ownCars,
                        begin_time: values["range-time-picker"][0].format("yyyy-MM-DD HH:mm:ss.000"),
                        end_time: values["range-time-picker"][1].format("yyyy-MM-DD HH:mm:ss.000"),
                        floor, number, status
                    }
                    makeReserve(obj)
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
    let title = ""
    switch (status) {
        case 1:
            title = "Free"
            break
        case 2:
            title = `Vehicle: ${vehicleID.name}`
            break
        case 3:
            title = "Reserved"
            break
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
                                title={<div style={{color: "white"}}>{title}</div>}
                                column={1}
                            >
                                {
                                    Object.entries(props).map(item => {
                                        // if (item[0] === vehicle_parking_id) return ""
                                        if (!item[1] || item[1] === true) return ""
                                        // console.log(item[1], "item1")
                                        if (item[1] instanceof Object) {
                                            return Object.entries(item[1]).map(item2 => {
                                                if (item2[0] === "userID") {
                                                    return <Descriptions.Item
                                                        label={"email"}
                                                        key={item2[0].id}
                                                        contentStyle={{color: "white"}}
                                                        labelStyle={{color: "white"}}
                                                    >{item2[1].email}</Descriptions.Item>
                                                }
                                                return <Descriptions.Item
                                                    label={item2[0]}
                                                    key={item2[0]}
                                                    contentStyle={{color: "white"}}
                                                    labelStyle={{color: "white"}}
                                                >{item2[1]}</Descriptions.Item>
                                            })
                                        }
                                        if (status !== 2 && status !== 3) {
                                            return <Descriptions.Item
                                                label={item[0]}
                                                key={item[0]}
                                                contentStyle={{color: "white"}}
                                                labelStyle={{color: "white"}}
                                            >{item[1]}</Descriptions.Item>
                                        } else {
                                            return <></>
                                        }

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
                                        return car.status !== 0
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
                                    {cars ? cars.filter(car => {
                                        return car.status !== 1
                                    }).map(car => {
                                        return <Option value={car.name}>{car.name}</Option>
                                    }) : ""}
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