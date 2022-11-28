import React from 'react';
import {Route, Routes} from "react-router-dom";
import NotFound from "./pages/NotFound";
import Walkin from "./pages/Walkin";
import MyLayout from "./layouts/MyLayout"

import Login from "./pages/Login";
import Register from "./pages/Register";
import RequireAuth from "./components/RequireAuth";
import Reserve from "./pages/Reserve";
import Home from "./pages/Home"

function App() {
    return (
        <Routes>
            <Route element={<MyLayout/>}>
                <Route path="*" element={<RequireAuth/>}>
                    <Route path="walkin" element={<Walkin/>}/>
                    <Route path="reserve" element={<Reserve/>}/>
                </Route>
            </Route>
            <Route path="/" element={<Home/>}/>
            <Route path="/login" element={<Login/>}/>
            <Route path="/register" element={<Register/>}/>
            <Route path="/*" element={<NotFound/>}/>
        </Routes>
    );
}

export default App;
