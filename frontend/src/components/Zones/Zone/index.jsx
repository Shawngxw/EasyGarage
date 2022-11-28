import React from 'react';
import Spaces from "./Spaces";
import {List} from "antd";

function Zone({parking_zone_title, parking_spaces}) {
	return (
		<List.Item>
			<List.Item.Meta
				title={
					<div style={{
						fontSize: 26,
						fontWeight: "bold"
					}}>{parking_zone_title}</div>
				}
				description={<Spaces parking_spaces={parking_spaces}/>}
			/>
		</List.Item>
	);
}

export default Zone;