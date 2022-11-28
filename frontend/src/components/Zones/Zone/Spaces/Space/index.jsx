import React, {useContext, useState} from 'react';
import {Col, Descriptions, Form, Input, Modal, Tooltip} from "antd";
import styles from "./index.module.less"
import {ZoneTypeCtx} from "../../../utils";


function Space(props) {
	const {parking_space_title, vehicle_parking_id, parking_space_reservation} = props
	let space_style = styles.colNotPark
	if (vehicle_parking_id) {
		space_style = parking_space_reservation ? styles.colReserve : styles.colParked
	}
	const [isModalOpen, setIsModalOpen] = useState(false)
	const [form] = Form.useForm()
	const zoneTypeCtx = useContext(ZoneTypeCtx)
	const handleOk = () => {
		setIsModalOpen(false);
		form
			.validateFields()
			.then((values) => {
				form.resetFields();
				console.log(values)
			})
			.catch((info) => {
				console.log('Validate Failed:', info);
			});
		console.log(zoneTypeCtx.zoneType)
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
					setIsModalOpen(true);
				}}
			>
				<Tooltip
					title={
						<div>
							<Descriptions
								title={
									<div
										style={{color: "white"}}>{vehicle_parking_id ? `Vehicle: ${vehicle_parking_id}` : "Free"}</div>
								}
								column={1}
							>
								{
									Object.entries(props).map(item => {
										if (item[0] === vehicle_parking_id) return ""
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
					<div className="title">{parking_space_title}</div>
				</Tooltip>
			</Col>
			<Modal
				title={zoneTypeCtx.zoneType}
				visible={isModalOpen}
				onOk={handleOk}
				onCancel={handleCancel}
				centered
			>
				<Form
					form={form}
					layout="vertical"
					name="form_in_modal"
					initialValues={{
						modifier: 'public',
					}}
				>
					<Form.Item
						name="title"
						label="Title"
						rules={[
							{
								required: true,
								message: 'Please input the title of collection!',
							},
						]}
					>
						<Input/>
					</Form.Item>
				</Form>
			</Modal>
		</>

	);
}

export default Space;