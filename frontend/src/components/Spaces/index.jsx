import React from 'react';
import {Row} from "antd";
import Space from "./Space";
import styles from "./index.module.less"

function Spaces() {
	const items = [1, 2, 3, 4, 5, 6, 7, 8, 9]
	return (
		<Row className={styles.row}>
			{items.map(item => {
				return <Space/>
			})}
		</Row>
	);
}

export default Spaces;