import React, {useEffect} from 'react';
import {useNavigate} from "react-router-dom";

function Home(props) {
	const navigate = useNavigate()
	useEffect(() => {
		setTimeout(() => {
			navigate("/walkin")
		}, 1500)
	})
	return (
		<div style={{
			display: "flex",
			justifyContent: "center",
			alignItems: "center",
			height: "100%",
			width: "100%",
			background: "url('./images/walcome.jpeg')"
		}}>
			<div
				style={{
					fontSize: 80,
					fontWeight: "bold",
					width: "100%",
					height: "100%",
					color: "#fff",
					background: "rgba(0,0,0,0.3)",
					display: "flex",
					justifyContent: "center",
					alignItems: "center"
				}}
			>Welcome to Easy Garage
			</div>
		</div>
	);
}

export default Home;