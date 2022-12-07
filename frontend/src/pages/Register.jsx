import {
	Button, Card,
	Form,
	Input, message,
} from 'antd';
import React from 'react';
import {useNavigate} from "react-router-dom";
import {useRegisterMutation} from "../api/registerApiSlice";

const formItemLayout = {
	labelCol: {
		xs: {
			span: 24,
		},
		sm: {
			span: 8,
		},
	},
	wrapperCol: {
		xs: {
			span: 24,
		},
		sm: {
			span: 16,
		},
	},
};
const tailFormItemLayout = {
	wrapperCol: {
		xs: {
			span: 24,
			offset: 0,
		},
		sm: {
			span: 16,
			offset: 8,
		},
	},
};

const Register = () => {
	const [form] = Form.useForm();
	const navigate = useNavigate()
	const [register, {isLoading}] = useRegisterMutation()

	const onFinish = async (values) => {
		try {
			const data = await register({...values})
			message.success("register successful!")
			navigate("/login")
		} catch (err) {
			if (!err?.originalStatus) {
				// isLoading: true until timeout occurs
				message.error('No Server Response');
			} else if (err.originalStatus === 400) {
				message.error('Missing Username or Password');
			}
		}
	};
	const handleLogin = () => {
		navigate("/login")
	}

	return (
		<div style={{
			height: "100%",
			width: "100%",
			display: "flex",
			justifyContent: "center",
			alignItems: "center",
			background: "skyblue"
		}}>
			<Card
				style={{
					width: 500,
				}}
				title="Register"
				extra={<Button onClick={handleLogin}>Login</Button>}
			>
				<Form
					{...formItemLayout}
					form={form}
					name="register"
					onFinish={onFinish}
					initialValues={{
						residence: ['zhejiang', 'hangzhou', 'xihu'],
						prefix: '86',
					}}
					scrollToFirstError
				>
					<Form.Item
						name="username"
						label="Name"
						rules={[
							{
								required: true,
								message: 'Please input your name!',
								whitespace: true,
							},
						]}
					>
						<Input/>
					</Form.Item>
					<Form.Item
						name="email"
						label="E-mail"
						rules={[
							{
								type: 'email',
								message: 'The input is not valid E-mail!',
							},
							{
								required: true,
								message: 'Please input your E-mail!',
							},
						]}
					>
						<Input/>
					</Form.Item>

					<Form.Item
						name="password"
						label="Password"
						rules={[
							{
								required: true,
								message: 'Please input your password!',
							},
						]}
						hasFeedback
					>
						<Input.Password/>
					</Form.Item>

					<Form.Item
						name="confirm"
						label="Confirm Password"
						dependencies={['password']}
						hasFeedback
						rules={[
							{
								required: true,
								message: 'Please confirm your password!',
							},
							({getFieldValue}) => ({
								validator(_, value) {
									if (!value || getFieldValue('password') === value) {
										return Promise.resolve();
									}

									return Promise.reject(new Error('The two passwords that you entered do not match!'));
								},
							}),
						]}
					>
						<Input.Password/>
					</Form.Item>
					<Form.Item {...tailFormItemLayout}>
						<Button type="primary" htmlType="submit">
							Register
						</Button>
						Or <a href="/login">login now!</a>
					</Form.Item>
				</Form>
			</Card>
		</div>

	);
};

export default Register;