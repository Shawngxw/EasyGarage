import {Button, Card, Form, Input, message} from 'antd';
import React from 'react';
import {useNavigate} from "react-router-dom";
import {useLoginMutation} from "../api/authApiSlice";
import {useDispatch} from "react-redux";
import {setCredentials} from "../store/authSlice";

const Login = () => {
	const navigate = useNavigate()
	const [login, { isLoading }] = useLoginMutation()
    const dispatch = useDispatch()

	const onFinish = async (values) => {
		try {
            const userData = await login({ username:values.username, password:values.password }).unwrap()
            dispatch(setCredentials({ ...userData, username:values.username }))
            navigate('/')
        } catch (err) {
            if (!err?.originalStatus) {
                // isLoading: true until timeout occurs
                message.error('No Server Response');
            } else if (err.originalStatus === 400) {
                message.error('Missing Username or Password');
            } else if (err.originalStatus === 401) {
                message.error('Unauthorized');
            } else {
                message.error('Login Failed');
            }
        }
	};

	const onFinishFailed = (errorInfo) => {
		console.log('Failed:', errorInfo);
	};
	const handleRegister = () => {
		navigate("/register")
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
					width: 500
				}}
				title="Login"
				extra={<Button onClick={handleRegister}>Sign up</Button>}
			>
				<Form
					name="basic"
					labelCol={{
						span: 8,
					}}
					wrapperCol={{
						span: 16,
					}}
					initialValues={{
						remember: true,
					}}
					onFinish={onFinish}
					onFinishFailed={onFinishFailed}
					autoComplete="off"
				>
					<Form.Item
						label="Username"
						name="username"
						rules={[
							{
								required: true,
								message: 'Please input your username!',
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
								message: 'Please input your password!',
							},
						]}
					>
						<Input.Password/>
					</Form.Item>

					<Form.Item
						wrapperCol={{
							offset: 8,
							span: 16,
						}}
					>
						<Button type="primary" htmlType="submit">
							Login
						</Button>
						Or <a href="#/register">register now!</a>
					</Form.Item>
				</Form>
			</Card>
		</div>

	);
};

export default Login;