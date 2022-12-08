import {Outlet, useLocation} from "react-router-dom";
import React from 'react';
import {Avatar, Layout, List, Menu, Popover} from "antd";
import Search from "antd/es/input/Search";
import {UserOutlined} from "@ant-design/icons";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {search} from "../store/searchSlice";
import styles from "./layout.module.less"
import {logOut, selectCurrentUser} from "../store/authSlice";

const Popup = () => {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const user = useSelector(selectCurrentUser)
    const handleLogout = () => {
        dispatch(logOut())
        navigate("/login")
    }
    const handleProfile = () => {
        navigate("/user")
    }
    const content = (
        <List header={<div>{user}</div>} className={styles.popupContent}>
            <List.Item onClick={handleProfile}>Profile</List.Item>
            <List.Item onClick={handleLogout}>Logout</List.Item>
        </List>
    );
    return (
        <Popover content={content}>
            <UserOutlined/>
        </Popover>
    )
}
const {Header, Content, Footer} = Layout
let items = [
    {
        label: "Walkin",
        key: "1"
    },
    {
        label: "Reserve",
        key: "2"
    }
]

function MyLayout() {
    const navigate = useNavigate()
    const dispatch = useDispatch()
    const location = useLocation()
    const handleClick = ({key}) => {
        switch (key) {
            case "1":
                navigate("/walkin")
                break
            case "2":
                navigate("/reserve")
                break
            default:
                break
        }
    }
    const handleSearch = (value) => {
        dispatch(search(value))
    }
    // 控制nav
    let selectedKeys
    if (location.pathname.indexOf("/reserve") !== -1) {
        selectedKeys = ["2"]
    } else if (location.pathname.indexOf("/walkin") !== -1) {
        selectedKeys = ["1"]
    } else {
        selectedKeys = []
    }


    return (
        <Layout>
            <Header className={styles.header}>
                <div className="left">
                    <div className="logo">Parking Management</div>
                    <Menu
                        selectedKeys={selectedKeys}
                        theme={"dark"}
                        items={items}
                        mode="horizontal"
                        style={{fontSize: 16, fontWeight: "bold", width: 300}}
                        onClick={handleClick}
                    ></Menu>
                </div>
                <div className="right">
                    <Search placeholder="Search zones" enterButton style={{width: 400}} size="large"
                            onSearch={handleSearch}/>
                    <Avatar size="large" icon={<Popup/>} style={{marginLeft: 20, marginRight: 40}}/>
                </div>
            </Header>
            <Content className={styles.content}>
                <Outlet/>
            </Content>
            <Footer className={styles.footer}>Powered by&nbsp;<a href="https://github.com/">Ou Yang</a>&nbsp;|&nbsp;<a
                href="https://policies.google.com/privacy">Privacy</a> </Footer>

        </Layout>

    );
}

export default MyLayout;
