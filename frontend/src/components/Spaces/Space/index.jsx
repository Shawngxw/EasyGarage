import React from 'react';
import {Col} from "antd";
import styles from  "./index.module.less"

function Space({title, state}) {
	return (
		<Col span={4} className={styles.colNotPark}>1</Col>
	);
}

export default Space;