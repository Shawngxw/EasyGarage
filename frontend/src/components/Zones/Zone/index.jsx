import React from 'react';
import Spaces from "./Spaces";
import {List} from "antd";

function Zone({floor, spaces}) {
    return (
        <List.Item>
            <List.Item.Meta
                title={
                    <div style={{
                        fontSize: 26,
                        fontWeight: "bold"
                    }}>{`Floor:${floor}`}</div>
                }
                description={<Spaces parking_spaces={spaces}/>}
            />
        </List.Item>
    );
}

export default Zone;