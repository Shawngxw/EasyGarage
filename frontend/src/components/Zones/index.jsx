import React from 'react';
import {List} from "antd";
import Zone from "./Zone";
// import data from "../../parking.json"
import styles from "./index.module.less"
import {ZoneTypeCtx} from "./utils";
import {useGetAllPlacesQuery} from "../../api/api";


function Zones({zoneType}) {
    const {data: floors, isFetching} = useGetAllPlacesQuery()
    if (isFetching) {
        return "Loading..."
    }
    return (
        <ZoneTypeCtx.Provider value={{zoneType}}>
            <div className={styles.legend}>
                <div> Legend:</div>
                <div className="parked">Parked</div>
                <div className="reserved">Reserved</div>
                <div className="free">Free</div>
            </div>
            <List>
                {Object.entries(floors).map(([floor, spaces]) => {
                    return <Zone key={floor} floor={floor} spaces={spaces}/>
                })}
            </List>
        </ZoneTypeCtx.Provider>
    );
}

export default Zones;