import React from 'react';
import {Row} from "antd";
import Space from "./Space";
import styles from "./index.module.less"

function Spaces({parking_spaces}) {
	return (
		<Row
			className={styles.row}
			gutter={{xs: 6, sm: 16, md: 24}}
			justify={"start"}
			align={"middle"}
		>
			{parking_spaces.map(parking_space => {
				return <Space key={parking_space.id} {...parking_space}/>
			})}
		</Row>
	);
}

export default Spaces;