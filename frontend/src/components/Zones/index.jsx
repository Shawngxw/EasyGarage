import React from 'react';
import {List} from "antd";
import Zone from "./Zone";
import data from "../../parking.json"
import styles from "./index.module.less"
import {ZoneTypeCtx} from "./utils";


function Zones({zoneType}) {
	const zones = data.parking_zones
	return (
		<ZoneTypeCtx.Provider value={{zoneType}}>
			<div className={styles.legend}>
				<div> Legend:</div>
				<div className="parked">Parked</div>
				<div className="reserved">Reserved</div>
				<div className="free">Free</div>
			</div>
			<List>
				{zones.map(zone => {
					return <Zone key={zone.parking_zone_id} {...zone}/>
				})}
			</List>
		</ZoneTypeCtx.Provider>
	);
}

export default Zones;